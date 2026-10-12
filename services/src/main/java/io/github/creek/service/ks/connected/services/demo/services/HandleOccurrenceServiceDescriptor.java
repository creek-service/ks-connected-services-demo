/*
 * Copyright 2021-2026 Creek Contributors (https://github.com/creek-service)
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
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.JSON_FORMAT;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.KAFKA_FORMAT;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.inputTopic;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.outputTopic;

import io.github.creek.service.ks.connected.services.demo.services.model.HandleUsage;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.topic.OwnedKafkaTopicInput;
import org.creekservice.api.kafka.metadata.topic.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.ComponentInput;
import org.creekservice.api.platform.metadata.ComponentOutput;
import org.creekservice.api.platform.metadata.ServiceDescriptor;

public final class HandleOccurrenceServiceDescriptor implements ServiceDescriptor {

    // Define the tweet-text input topic, conceptually owned by this service:
    public static final OwnedKafkaTopicInput<Long, String> RawTweetStream =
            inputTopic(
                    "twitter.tweet.text", // Topic name
                    Long.class, // Topic key: Tweet id (Kafka native)
                    KAFKA_FORMAT,
                    String.class, // Topic value: Tweet text (Kafka native)
                    KAFKA_FORMAT,
                    withPartitions(5)); // Topic config

    // Define the output topic, again conceptually owned by this service:
    public static final OwnedKafkaTopicOutput<String, HandleUsage> TweetHandleUsageStream =
            outputTopic(
                    "twitter.handle.usage",
                    String.class, // Twitter handle (Kafka native)
                    KAFKA_FORMAT,
                    HandleUsage.class,
                    JSON_FORMAT, // Usage data (JSON)
                    withPartitions(6).withRetentionTime(Duration.ofHours(12)));

    public HandleOccurrenceServiceDescriptor() {}

    @Override
    public String dockerImage() {
        return "ghcr.io/creek-service/ks-connected-services-demo-handle-occurrence-service";
    }

    @Override
    public Collection<ComponentInput> inputs() {
        return List.of(RawTweetStream);
    }

    @Override
    public Collection<ComponentOutput> outputs() {
        return List.of(TweetHandleUsageStream);
    }
}
