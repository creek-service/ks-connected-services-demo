---
title: Define the service's resources
permalink: /descriptor
description: Learn how to create a service descriptor using the resources defined in other service descriptors.
layout: single
snippet_comment_prefix: "//"
---

The `Add service module` workflow, ran in the previous step, created a `HandleOccurrenceFilteringServiceDescriptor` 
[service descriptor][serviceDescriptors] in the repository's `services` module.

The steps below flesh out the service's descriptor with it's input and output topics. 
Crucially, this will define the filter service's input topic using the `handle-occurrence-service`'s output topic descriptor.
This is one of the two key features of this tutorial. The other being [system testing multiple services][sysTestStep].

## Define the JSON payload

Rather than reuse the primitive types used by the previous tutorial, topic values in this tutorial are schema-validated
JSON payloads. This gives Creek something to validate incoming and outgoing records against, and gives consumers of a
topic a documented, machine-checkable contract for its value, instead of just a `String` or an `Integer`.

The `api` module defines the Java types used as topic values. Each type that should have a JSON schema generated for it
is annotated with [`@GeneratesSchema`][generatesSchema]:

{% highlight java %}
{% include_snippet handle-usage from ../api/src/main/java/io/github/creek/service/ks/connected/services/demo/api/model/HandleUsage.java %}
{% endhighlight %}

**ProTip:** Keep validation of invariants in the type's constructor, e.g. a compact constructor on a `record`. This is
the validation the generated JSON schema is checked for consistency against.
{: .notice--info}

The `api` module's `build.gradle.kts` applies the `org.creekservice.schema.json` Gradle plugin, which adds the
`generateJsonSchema` task. This scans the module for `@GeneratesSchema`-annotated types and writes out a JSON schema
for each:

```kotlin
plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

dependencies {
    implementation("org.creekservice:creek-base-annotation:$creekVersion")
    jsonSchemaGenerator("org.creekservice:creek-json-schema-generator:$creekVersion")
}

creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
```

Restricting the class and module path scanning to the `api` module's own module name, as above, keeps schema
generation fast, by avoiding scanning the whole class path for annotated types.

Finally, the JSON serde used at runtime is backed by [Jackson][jackson], which needs reflective access to a type's
canonical constructor and component accessors. As the `api` module is a JPMS module, this access must be explicitly
granted in its `module-info.java`:

```java
// Required so Jackson (used by the JSON serde) can reflectively access the record's canonical
// constructor and component accessors at runtime.
opens io.github.creek.service.ks.connected.services.demo.api.model;
```

## Define the topic resources

The aggregate template provided a shell service descriptor in the repository named `HandleOccurrenceFilteringServiceDescriptor`.
Add the following to the class to declare the service's input and output topics:

{% highlight java %}
{% include_snippet class-name from ../services/src/main/java/io/github/creek/service/ks/connected/services/demo/services/HandleOccurrenceFilteringServiceDescriptor.java %}

{% include_snippet topic-resources from ../services/src/main/java/io/github/creek/service/ks/connected/services/demo/services/HandleOccurrenceFilteringServiceDescriptor.java %}

    ...
}
{% endhighlight %}

Importantly, note how this descriptor's `HandleUsageStream` topic descriptor, (step #1 in the code above), 
is created by calling `toInput()` on the `HandleOccurrenceServiceDescriptor`'s `TweetHandleUsageStream` output topic descriptor.
Compare this to the explicit declaration of the `TweetHandleUsagePresidentsStream` output topic, (step #2 in the code above),
which declares a new, previously unseen, topic, using `outputTopic(...)`.

The `toInput()` method, called in step #1, returns an _unowned_ input topic descriptor, with the correct name and types.
Whereas, the output topic descriptor, created in step #2, is an _owned_ topic descriptor, _owned_
by this new filter service.

**ProTip:** The concept of topic _ownership_ defines which service / aggregate, and hence team within an organisation,
is responsible for the topic, its configuration, and the data it contains.
{: .notice--info}

In this case, by declaring the filter services input by calling `toInput()` on the occurrence service's output topic, 
we're declaring the filter service as a _downstream_ consumer of the occurrence service.

Conversely, though less common, service's can define _owned_ input topics. In such a situation, _upstream_ services
can declare that they _produce_ to the topic by calling `toOuput()` on the input topic descriptor when
declaring their own output topic descriptor.

The eagle-eyed of you may also have noticed that the service's output topic, (step #2 in the code above), declares
the topic's key and value types by referencing the input topic's key and value types.
This is just a convenient & type-safe way of ensuring the key and value types of these two topics align, given
that the output topic is simply a filtered view of the input topic.

`outputTopic(...)` defaults to a schema-validated JSON value and a Kafka-native key.
For a Kafka-native value instead, use the overload accepting explicit key and value formats,
as the occurrence service does for its `twitter.tweet.text` input topic.

**ProTip:** _Ownership_ applies to a topic's JSON schema too, and it follows the ownership of the topic, not of the
Java type used as the value. `HandleUsageStream`, (step #1 above), is an _unowned_ input, so its JSON schema is
tracked as _unowned_ too: it remains owned by the `handle-occurrence-service`, which owns the topic it's derived
from. `HandleUsagePresidentsStream`, (step #2 above), is a new, _owned_, output, so even though it reuses the same
`HandleUsage` Java type, it gets its own, independently owned, JSON schema.
{: .notice--info}

[creekExts]: https://www.creekservice.org/extensions/
[aggDescriptor]: https://www.creekservice.org/docs/descriptors/#aggregate-descriptor
[serviceDescriptors]: https://www.creekservice.org/docs/descriptors/#service-descriptor
[sysTestStep]: {{ "/system-testing" | relative_url }}
[generatesSchema]: https://www.creekservice.org/creek-json-schema/
[jackson]: https://github.com/FasterXML/jackson
