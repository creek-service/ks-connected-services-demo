---
title: Further reading
permalink: /further-reading
description: Recommended reading for once you've completed this Creek tutorial.
layout: single
---

This tutorial has covered the basics of linking service's _within_ an aggregate together. 
The [Kafka Streams: aggregate api tutorial](/ks-aggregate-api-demo/) covers defining an aggregate's api, 
so that other aggregates can make use of it.

If you've not already done so, completing the
[Basic Kafka Streams Tutorial]({{ site.url | append: "/basic-kafka-streams-demo/" }})
covers useful core Creek features not covered by this tutorial. For example:
 - [Bootstrapping a new repository]({{ site.url | append: "/basic-kafka-streams-demo/bootstrap" }})
 - [Capturing code coverage metrics from system tests]({{ site.url | append: "/basic-kafka-streams-demo/system-testing-coverage" }})
 - [Debugging services, running in Docker containers, while running system tests]({{ site.url | append: "/basic-kafka-streams-demo/debugging" }})
 - [Unit testing Kafka Streams topologies]({{ site.url | append: "/basic-kafka-streams-demo/unit-testing" }})

Additional tutorials will be added over time. These can be found on the [tutorials page]({{ site.url | append: "/tutorials/" }}).

This tutorial demonstrated schema-validated JSON payloads for topic values, alongside the simple, natively-serialized,
types like `String` used by the previous tutorial. See the [JSON schema format section][kafkaJsonSchemaDocs] of the
`creek-kafka` docs for more on the JSON serialization support used in this tutorial.
{: .notice--info}

[kafkaJsonSchemaDocs]: https://www.creekservice.org/creek-kafka/#json-schema-format