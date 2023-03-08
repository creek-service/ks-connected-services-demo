---
title: Kafka Streams Connected Services Demo
permalink: /
layout: single
toc: true
---

This tutorial will lead you through extending the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo) with a second
microservice that consumes the output of the first.

The first service, added in the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo), detected the use of Twitter
handles, e.g. `@elonmusk`, in Tweets and outputs a streams of messages to the `handle-occurrence-service` Kafka topic, 
where each message is keyed on the Twitter handle and the value stores the number of concurrences of the handle in a Tweet.

The additional service we'll add in this tutorial will consume the `handle-occurrence-service` Kafka topic and will 
filter out any handles not associated with a hard-coded list of USA presidents.

**Note:** This is a deliberately simplistic service, allowing the tutorial to focus on demonstrating Creek's features.
{: .notice--warning}

## Features covered

By the end of this tutorial you should know:
  * How to add new microservices to an aggregate repository.
  * How to define a service descriptor that uses resources, e.g. Kafka topics, defined in another service descriptor.
  * How to obtain a `kafka` topic's serde, for use in a Kafka Streams topologies.
  * How to build and execute a Kafka Streams topology, using Creek.
  * How to write black-box system tests that cover the functionality of multiple services working together.
  
## Prerequisites

The tutorial requires the following:

* A [GitHub](https://github.com/join) account.
* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) installed for source code control.
* [Docker desktop](https://docs.docker.com/desktop/) installed for running containerised system tests.

## Design

To keep things simple, the new service we'll add in this tutorial will perform a simple stateless filtering operation:
filtering out any Twitter handle occurrences that aren't from an allowed set of handles. This is deliberately simplistic
to allow us to focus more on how to connect two services, rather than the business logic of the service itself.

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

The completed tutorial can be viewed [on GitHub][demoOnGh].

[<i class="fab fa-fw fa-github"/>&nbsp; View on GitHub][demoOnGh]{: .btn .btn--success}

[demoOnGh]: https://github.com/creek-service/ks-connected-services-demo