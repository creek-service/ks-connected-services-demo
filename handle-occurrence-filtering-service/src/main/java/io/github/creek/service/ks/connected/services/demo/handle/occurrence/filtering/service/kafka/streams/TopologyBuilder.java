/*
 * Copyright 2022-2025 Creek Contributors (https://github.com/creek-service)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.creek.service.ks.connected.services.demo.handle.occurrence.filtering.service.kafka.streams;

import static io.github.creek.service.ks.connected.services.demo.services.HandleOccurrenceFilteringServiceDescriptor.HandleUsagePresidentsStream;
import static io.github.creek.service.ks.connected.services.demo.services.HandleOccurrenceFilteringServiceDescriptor.HandleUsageStream;
import static java.util.Objects.requireNonNull;
import static org.creekservice.api.kafka.metadata.KafkaTopicDescriptor.DEFAULT_CLUSTER_NAME;

import java.util.Set;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.creekservice.api.kafka.extension.resource.KafkaTopic;
import org.creekservice.api.kafka.streams.extension.KafkaStreamsExtension;
import org.creekservice.api.kafka.streams.extension.util.Name;

public final class TopologyBuilder {

    private final KafkaStreamsExtension ext;
    private final Name name = Name.root();

    public TopologyBuilder(final KafkaStreamsExtension ext) {
        this.ext = requireNonNull(ext, "ext");
    }

    // begin-snippet: build-method
    public Topology build() {
        final StreamsBuilder builder = new StreamsBuilder();

        // Pass a topic descriptor to the Kafka Streams extension to
        // obtain a typed `KafkaTopic` instance, which provides access to serde:
        final KafkaTopic<String, Integer> input = ext.topic(HandleUsageStream);
        final KafkaTopic<String, Integer> output = ext.topic(HandleUsagePresidentsStream);

        // Build a simple topology:
        // Consume input topic:
        builder.stream(
                        input.name(),
                        Consumed.with(input.keySerde(), input.valueSerde())
                                .withName(name.name("ingest-" + input.name())))
                // filter the input, allowing only USA presidents:
                .filter(this::presidentsOnly, name.named("filter-out-non-presidents"))
                // Finally, produce to output:
                .to(
                        output.name(),
                        Produced.with(output.keySerde(), output.valueSerde())
                                .withName(name.name("egress-" + output.name())));

        // Grab the cluster properties from Creek to build and return the Topology:
        return builder.build(ext.properties(DEFAULT_CLUSTER_NAME));
    }
    // end-snippet

    // begin-snippet: filter-method
    private static final Set<String> PRESIDENT_HANDLES =
            Set.of("@POTUS", "@JoeBiden", "@realDonaldTrump", "@BarackObama");

    private boolean presidentsOnly(final String twitterHandle, final Object ignored) {
        return PRESIDENT_HANDLES.contains(twitterHandle);
    }
    // end-snippet
}
