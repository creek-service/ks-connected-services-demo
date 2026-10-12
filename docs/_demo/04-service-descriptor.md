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
Compare this to the explicit declaration of the `HandleUsagePresidentsStream` output topic, (step #2 in the code above),
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
can declare that they _produce_ to the topic by calling `toOutput()` on the input topic descriptor when
declaring their own output topic descriptor.

The eagle-eyed of you may also have noticed that the service's output topic, (step #2 in the code above), declares
the topic's key and value types and formats by referencing those of the input topic.
This is just a convenient & type-safe way of ensuring the key and value types and formats of these two topics align, given
that the output topic is simply a filtered view of the input topic.

**ProTip:** _Ownership_ applies to a topic's JSON schema too, and it follows the ownership of the topic.
`HandleUsageStream`, (step #1 above), is an _unowned_ input, so its JSON schema is
tracked as _unowned_ too: it remains owned by the `handle-occurrence-service`, which owns the topic it's derived
from. `HandleUsagePresidentsStream`, (step #2 above), is a new, _owned_, output, so even though it reuses the same
`HandleUsage` Java type, it gets its own, independently owned, JSON schema, which can be evolved independently.
{: .notice--info}

[creekExts]: https://www.creekservice.org/extensions/
[aggDescriptor]: https://www.creekservice.org/docs/descriptors/#aggregate-descriptor
[serviceDescriptors]: https://www.creekservice.org/docs/descriptors/#service-descriptor
[sysTestStep]: {{ "/system-testing" | relative_url }}
