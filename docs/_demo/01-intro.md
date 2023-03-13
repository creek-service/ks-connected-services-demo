---
title: Kafka Streams Connected Services Tutorial
permalink: /
layout: single
header:
    overlay_color: "#000"
    overlay_filter: "0.5"
    overlay_image: /assets/images/tutorial-ks-connect-services.svg
excerpt: Learn how to develop and test the functionality of multiple Kafka Streams microservices working together to deliver business functionality. 
toc: true
---

This tutorial will lead you through extending the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo/) with a second
microservice that consumes the output of the first.

The first service, added in the [previous tutorial](/basic-kafka-streams-demo/), detected the use of Twitter
handles, e.g. `@elonmusk`, in Tweets. Twitter handle occurrences were output to the `twitter.handle.usage` Kafka topic.
Records on this topic have the Twitter handle in their key and the number of concurrences in the value.

This tutorial will add a new filtering service. This new service will consume the output topic of the existing service,
and filter out any handles not associated with a hard-coded list of USA presidents.

**Note:** This is a deliberately simplistic service, allowing the tutorial to focus on demonstrating Creek's features.
{: .notice--warning}

## Features covered

This tutorial will touch on many of Creek's features and techniques cover by previous tutorials. 
However, the key features this tutorial is designed to highlight are:
  * How to declare a service that uses resources, e.g. Kafka topics, declared, and owned, by another service.
    This tutorial will declare a service that consumes the output of another service.
    If you wish to jump straight to the step covering this, see the [Service descriptor][svcDescStep] step.
  * How to write black-box system tests that cover the functionality of multiple services working together.
    If you wish to jump straight to the step covering this, see the [System test][sysTestStep] step.

In addition to the above key features, by the end of this tutorial you should also know:
  * How to add new microservices to an aggregate repository.
  * How to obtain a `kafka` topic's serde, for use in a Kafka Streams topologies.
  * How to build and execute a Kafka Streams topology, using Creek.
  
## Prerequisites

The tutorial requires the following:

* A [GitHub <i class="fas fa-external-link-alt"></i>](https://github.com/join){:target="_blank"} account.
* [Git <i class="fas fa-external-link-alt"></i>](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git){:target="_blank"} installed for source code control.
* [Docker desktop <i class="fas fa-external-link-alt"></i>](https://docs.docker.com/desktop/){:target="_blank"} installed for running containerised system tests.

## Design

To keep things simple, the new service added in this tutorial will perform a simple stateless filtering operation:
filtering out any records with Twitter handles that aren't in a whitelist. 
By keeping the business logic deliberately simplistic, the tutorial can focus on its key objectives: to demonstrate
linking services and system testing linked services.

The tutorial will lead you through adding a second `handle-occurrence-filtering-service` alongside the existing 
`handle-occurrence-service`. This new service will consume the `twitter.handle.usage` Kafka topic, owned and populated 
by the `handle-occurrence-service`.

**ProTip:** The concept of topic _ownership_ defines which service, or aggregate, and hence team within an organisation,
is responsible for a topic, its configuration, and the data it contains.
{: .notice--info}

{% include figure image_path="/assets/images/creek-demo-design.svg" alt="Service design" %}

The new service will forward records consumed from its input to its own `twitter.handle.usage.presidents` output 
Kafka topic, but only if the record's Twitter handle is in a hardcoded list of allowed handles.
The allowed set of Twitter handles will be those of past and present precedents of the USA.

## Complete solution

The completed tutorial can be viewed [on GitHub <i class="fas fa-external-link-alt"></i>][demoOnGh]{:target="_blank"}.

[<i class="fab fa-fw fa-github"/>&nbsp; View on GitHub][demoOnGh]{: .btn .btn--success}{:target="_blank"}

[demoOnGh]: https://github.com/creek-service/ks-connected-services-demo
[svcDescStep]: {{ "/descriptor" | relative_url }}
[sysTestStep]: {{ "/system-testing" | relative_url }}