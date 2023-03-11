---
title: Further reading
permalink: /further-reading
description: Recommended reading for once you've completed this Creek tutorial.
layout: single
---

This tutorial has covered the basics of linking service's _within_ an aggregate together. A
[planned tutorial <i class="fas fa-external-link-alt"></i>](https://github.com/creek-service/creek-kafka/issues/259){:target="_blank"} 
will cover linking different aggregates.

If you've not already done so, completing the
[Basic Kafka Streams Tutorial]({{ site.url | append: "/basic-kafka-streams-demo/" }})
covers useful core Creek features not covered by this tutorial. For example:
 - [Bootstrapping a new repository]({{ site.url | append: "/basic-kafka-streams-demo/bootstrap" }})
 - [Capturing code coverage metrics from system tests]({{ site.url | append: "/basic-kafka-streams-demo/system-testing-coverage" }})
 - [Debugging services, running in Docker containers, while running system tests]({{ site.url | append: "/basic-kafka-streams-demo/debugging" }})
 - [Unit testing Kafka Streams topologies]({{ site.url | append: "/basic-kafka-streams-demo/unit-testing" }})

Additional tutorials will be added over time. These can be found on the [tutorials page]({{ site.url | append: "/tutorials/" }}).

The payloads used in this tutorial were simple types like `Integer` and `String`.  
Work to extend this to more complex types using, schema validated, JSON serialization, will be 
[starting soon <i class="fas fa-external-link-alt"></i>](https://github.com/creek-service/creek-kafka/issues/25){:target="_blank"}.
{: .notice--info}