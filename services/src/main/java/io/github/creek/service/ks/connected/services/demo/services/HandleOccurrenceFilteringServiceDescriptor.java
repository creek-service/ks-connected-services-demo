/*
 * Copyright 2021-2022 Creek Contributors (https://github.com/creek-service)
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

package io.github.creek.service.ks.connected.services.demo.services;

import static io.github.creek.service.ks.connected.services.demo.internal.TopicConfigBuilder.withPartitions;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.outputTopic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.KafkaTopicInput;
import org.creekservice.api.kafka.metadata.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.ComponentInput;
import org.creekservice.api.platform.metadata.ComponentInternal;
import org.creekservice.api.platform.metadata.ComponentOutput;
import org.creekservice.api.platform.metadata.ServiceDescriptor;

// begin-snippet: class-name
public final class HandleOccurrenceFilteringServiceDescriptor implements ServiceDescriptor {
    // end-snippet
    private static final List<ComponentInput> INPUTS = new ArrayList<>();
    private static final List<ComponentInternal> INTERNALS = new ArrayList<>();
    private static final List<ComponentOutput> OUTPUTS = new ArrayList<>();

    // formatting:off
// begin-snippet: topic-resources
    // 1. Hookup the handle-occurrence-service's output as this services, unowned, input:
    public static final KafkaTopicInput<String, Integer> HandleUsageStream =
            register(HandleOccurrenceServiceDescriptor.TweetHandleUsageStream.toInput());

    // 2. Define the filtered output topic, conceptually owned by this service:
    public static final OwnedKafkaTopicOutput<String, Integer> HandleUsagePresidentsStream =
            register(outputTopic(
                    "twitter.handle.usage.presidents",
                    HandleUsageStream.key().type(),
                    HandleUsageStream.value().type(),
                    withPartitions(3)));
// end-snippet
    // formatting:on

    public HandleOccurrenceFilteringServiceDescriptor() {}

    @Override
    public String dockerImage() {
        return "ghcr.io/creek-service/ks-connected-services-demo-handle-occurrence-filtering-service";
    }

    @Override
    public Collection<ComponentInput> inputs() {
        return List.copyOf(INPUTS);
    }

    @Override
    public Collection<ComponentInternal> internals() {
        return List.copyOf(INTERNALS);
    }

    @Override
    public Collection<ComponentOutput> outputs() {
        return List.copyOf(OUTPUTS);
    }

    private static <T extends ComponentInput> T register(final T input) {
        INPUTS.add(input);
        return input;
    }

    // Uncomment if needed:
    // private static <T extends ComponentInternal> T register(final T internal) {
    //     INTERNALS.add(internal);
    //     return internal;
    // }

    private static <T extends ComponentOutput> T register(final T output) {
        OUTPUTS.add(output);
        return output;
    }
}
