---
title: Define the service's resources
permalink: /descriptor
description: Learn how to create a service descriptor using the resources defined in other service descriptors.
layout: single
snippet_comment_prefix: "//"
snippet_source: "../services/src/main/java/io/github/creek/service/ks/connected/services/demo/services/HandleOccurrenceFilteringServiceDescriptor.java"
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
{% include_snippet class-name %}

{% include_snippet topic-resources %}

    ...
}
{% endhighlight %}

Importantly, note how this descriptor's `HandleUsageStream` topic descriptor, (step #1 in the code above), 
is created by calling `toInput()` on the `HandleOccurrenceServiceDescriptor`'s `TweetHandleUsageStream` output topic descriptor.
Compare this to the explicit declaration of the `TweetHandleUsagePresidentsStream` output topic, (step #2 in the code above),
which declares a new, previously unseen, topic

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

[creekExts]: https://www.creekservice.org/extensions/
[aggDescriptor]: https://www.creekservice.org/docs/descriptors/#aggregate-descriptor
[serviceDescriptors]: https://www.creekservice.org/docs/descriptors/#service-descriptor
[sysTestStep]: {{ "/system-testing" | relative_url }}