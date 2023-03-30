---
title: Write the business logic
permalink: /business-logic
description: Adds deliberately trivial business logic, as this step is not the focus of this tutorial 
layout: single
snippet_comment_prefix: "//"
snippet_source: "../handle-occurrence-filtering-service/src/main/java/io/github/creek/service/ks/connected/services/demo/handle/occurrence/filtering/service/kafka/streams/TopologyBuilder.java"
---

This step will add the business logic for the new service, in the form of a simple [Kafka Streams][kafkaStreams] topology. 

As this is not the focus of this tutorial, this logic is kept deliberately trivial: 
it will filter the input topic, and produce the resulting records to the service's output topic.
The filter will exclude any records with Twitter handles not in a hardcoded list of accounts linked to presidents of the USA. 

As a reminder, the input records store the Twitter handle in the record's key and the number of occurrences in the record's value. 
The service's output topic will follow the same schema.

## Define the stream topology

The `Add service module` provided a shell `TopologyBuilder` class in the new `handle-occurrence-filtering-service` module.
Flesh out the class's `build` method to match what's below:

{% highlight java %}
{% include_snippet build-method %}
{% endhighlight %}

**ProTip:** The example code _deliberately_ names each step in the topology. This is good practice.
Relying on default naming can result in topology evolution issues in the future.
Internal store and topic names incorporate the step name. With default naming, the name of a step, and hence the store or topic,
can change if steps are added or removed.
This can lead to unintentional changes in internal topic names.
If such a change was deployed, any unprocessed messages in the old topics would be skipped.
{: .notice--info}

The above topology consumes the `HandleUsageStream` defined in the service's descriptor, 
filters it using the `presidentsOnly` method, 
and produces any output to the `HandleUsagePresidentsStream`.

The [Creek Kafka Streams extension][ksExt] provides type-safe access to the topic metadata & serde,
and Kafka cluster properties, allowing engineers and the code to focus on the business logic.

The details of the `presidentsOnly` method isn't particularly important in the context of this tutorial.
A simple solution might look like this:

{% highlight java %}
{% include_snippet filter-method %}
{% endhighlight %}

...and that's the production code of the service complete!

**ProTip:** The `Name` instance defined in the `TopologyBuilder` doesn't add much in this example, but as topologies 
get more complex, getting broken down into multiple builder classes, it really comes into its own. 
Check out [its JavaDoc <i class="fas fa-external-link-alt"></i>][nameJavaDocs]{:target="_blank"} to see how it can be used to help avoid topology node name clashes.
{: .notice--info}

## Topology builder unit test

Unit testing is not the focus of this tutorial. However, as it stands, the unit tests will fail.
For completeness, this is addressed below.

Unit testing of Kafka Streams topologies is covered in more detail in the
[Basic Kafka Streams Tutorial <i class="fas fa-external-link-alt"></i>][basicKsDemo-UnitTesting]{:target="_blank"}.
{: .notice--info}

The `Add service workflow` added a new `TopologyBuilderTest` for the new service's topology.
This comes with a `shouldNotChangeTheTopologyUnintentionally` test which, as it's JavaDoc states, is there to capture
unintentional changes to the topology. Unintentional changes could introduce the possibility of data-loss, if
deployed.

The test compares the topology with the last know topology and fails if they differ.
If the change is intentional, then the `handle-occurrence-filtering-service/src/test/resources/kafka/streams/expected_topology.txt`
file can be updated to reflect the latest topology.

For this tutorial, the test can simple be disabled or deleted.

[nameJavaDocs]: https://javadoc.io/doc/org.creekservice/creek-kafka-streams-extension/latest/creek.kafka.streams.extension/org/creekservice/api/kafka/streams/extension/util/Name.html
[kafkaStreams]: https://kafka.apache.org/documentation/streams/
[ksExt]: https://www.creekservice.org/creek-kafka
[basicKsDemo-UnitTesting]: {{ site.url | append: "/basic-kafka-streams-demo/unit-testing" }}
[todo]: switch about links to proper creekservice.org links once each repo publishes docs.