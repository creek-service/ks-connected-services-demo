/*
 * Copyright 2021-2025 Creek Contributors (https://github.com/creek-service)
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

package io.github.creek.service.ks.connected.services.demo.internal;

import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.JSON_FORMAT;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.KAFKA_FORMAT;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.creatableInternalTopic;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.inputTopic;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.internalTopic;
import static io.github.creek.service.ks.connected.services.demo.internal.TopicDescriptors.outputTopic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.when;

import java.util.List;
import org.creekservice.api.kafka.metadata.schema.OwnedJsonSchemaDescriptor;
import org.creekservice.api.kafka.metadata.schema.UnownedJsonSchemaDescriptor;
import org.creekservice.api.kafka.metadata.topic.CreatableKafkaTopicInternal;
import org.creekservice.api.kafka.metadata.topic.KafkaTopicConfig;
import org.creekservice.api.kafka.metadata.topic.KafkaTopicInput;
import org.creekservice.api.kafka.metadata.topic.KafkaTopicInternal;
import org.creekservice.api.kafka.metadata.topic.KafkaTopicOutput;
import org.creekservice.api.kafka.metadata.topic.OwnedKafkaTopicInput;
import org.creekservice.api.kafka.metadata.topic.OwnedKafkaTopicOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TopicDescriptorsTest {

    private static final KafkaTopicConfig CONFIG = TopicConfigBuilder.withPartitions(1).build();

    @Mock private TopicConfigBuilder config;

    @BeforeEach
    void setUp() {
        when(config.build()).thenReturn(CONFIG);
    }

    @Test
    void shouldCreateInputTopic() {
        // When:
        final OwnedKafkaTopicInput<Long, String> topic =
                inputTopic("name", Long.class, KAFKA_FORMAT, String.class, KAFKA_FORMAT, config);

        // Then:
        assertThat(topic.id().toString(), is("kafka-topic://default/name"));
        assertThat(topic.name(), is("name"));
        assertThat(topic.key().format(), is(KAFKA_FORMAT));
        assertThat(topic.key().type(), is(Long.class));
        assertThat(topic.key().resources().toList(), empty());
        assertThat(topic.value().format(), is(KAFKA_FORMAT));
        assertThat(topic.value().type(), is(String.class));
        assertThat(topic.value().resources().toList(), empty());
        assertThat(topic.config(), is(sameInstance(CONFIG)));
    }

    @Test
    void shouldConvertInputTopicToOutput() {
        // Given:
        final OwnedKafkaTopicInput<Long, String> input =
                inputTopic("name", Long.class, KAFKA_FORMAT, String.class, KAFKA_FORMAT, config);

        // When:
        final KafkaTopicOutput<Long, String> output = input.toOutput();

        // Then:
        assertThat(output.id().toString(), is("kafka-topic://default/name"));
        assertThat(output.name(), is("name"));
        assertThat(output.key().format(), is(KAFKA_FORMAT));
        assertThat(output.key().type(), is(Long.class));
        assertThat(output.value().format(), is(KAFKA_FORMAT));
        assertThat(output.value().type(), is(String.class));
    }

    @Test
    void shouldCreateInternalTopic() {
        // When:
        final KafkaTopicInternal<Long, String> topic =
                internalTopic("name", Long.class, KAFKA_FORMAT, String.class, KAFKA_FORMAT);

        // Then:
        assertThat(topic.name(), is("name"));
        assertThat(topic.key().format(), is(KAFKA_FORMAT));
        assertThat(topic.key().type(), is(Long.class));
        assertThat(topic.value().format(), is(KAFKA_FORMAT));
        assertThat(topic.value().type(), is(String.class));
    }

    @Test
    void shouldCreateCreatableInternalTopic() {
        // When:
        final CreatableKafkaTopicInternal<Long, String> topic =
                creatableInternalTopic(
                        "name", Long.class, KAFKA_FORMAT, String.class, KAFKA_FORMAT, config);

        // Then:
        assertThat(topic.name(), is("name"));
        assertThat(topic.key().format(), is(KAFKA_FORMAT));
        assertThat(topic.key().type(), is(Long.class));
        assertThat(topic.value().format(), is(KAFKA_FORMAT));
        assertThat(topic.value().type(), is(String.class));
        assertThat(topic.config(), is(sameInstance(CONFIG)));
    }

    @Test
    void shouldCreateOutputTopic() {
        // When:
        final OwnedKafkaTopicOutput<Long, String> topic =
                outputTopic("name", Long.class, KAFKA_FORMAT, String.class, KAFKA_FORMAT, config);

        // Then:
        assertThat(topic.id().toString(), is("kafka-topic://default/name"));
        assertThat(topic.name(), is("name"));
        assertThat(topic.key().format(), is(KAFKA_FORMAT));
        assertThat(topic.key().type(), is(Long.class));
        assertThat(topic.value().format(), is(KAFKA_FORMAT));
        assertThat(topic.value().type(), is(String.class));
        assertThat(topic.config(), is(sameInstance(CONFIG)));
    }

    @Test
    void shouldConvertOutputTopicToInput() {
        // Given:
        final OwnedKafkaTopicOutput<Long, String> output =
                outputTopic("name", Long.class, KAFKA_FORMAT, String.class, KAFKA_FORMAT, config);

        // When:
        final KafkaTopicInput<Long, String> input = output.toInput();

        // Then:
        assertThat(input.id().toString(), is("kafka-topic://default/name"));
        assertThat(input.name(), is("name"));
        assertThat(input.key().format(), is(KAFKA_FORMAT));
        assertThat(input.key().type(), is(Long.class));
        assertThat(input.value().format(), is(KAFKA_FORMAT));
        assertThat(input.value().type(), is(String.class));
    }

    @Test
    void shouldDefaultOutputTopicValueToJson() {
        // When:
        final OwnedKafkaTopicOutput<Long, String> topic =
                outputTopic("name", Long.class, String.class, config);

        // Then:
        assertThat(topic.key().format(), is(KAFKA_FORMAT));
        assertThat(topic.key().resources().toList(), empty());
        assertThat(topic.value().format(), is(JSON_FORMAT));

        final List<?> schemas = topic.value().resources().toList();
        assertThat(schemas, contains(instanceOf(OwnedJsonSchemaDescriptor.class)));
        final OwnedJsonSchemaDescriptor<?> schema = (OwnedJsonSchemaDescriptor<?>) schemas.get(0);
        assertThat(schema.schemaRegistryName(), is("default"));
        assertThat(schema.part(), is(sameInstance(topic.value())));
    }

    @Test
    void shouldDefaultInputTopicValueToJson() {
        // When:
        final OwnedKafkaTopicInput<Long, String> topic =
                inputTopic("name", Long.class, String.class, config);

        // Then:
        assertThat(topic.key().format(), is(KAFKA_FORMAT));
        assertThat(topic.value().format(), is(JSON_FORMAT));
        assertThat(
                topic.value().resources().toList(),
                contains(instanceOf(OwnedJsonSchemaDescriptor.class)));
    }

    @Test
    void shouldTrackUnownedJsonSchemaWhenOwnedOutputConvertedToInput() {
        // Given:
        final OwnedKafkaTopicOutput<Long, String> output =
                outputTopic("name", Long.class, String.class, config);

        // When:
        final KafkaTopicInput<Long, String> input = output.toInput();

        // Then: the schema remains owned by the service that owns the output topic, so from this
        // (consuming) descriptor's point of view it is unowned:
        assertThat(
                input.value().resources().toList(),
                contains(instanceOf(UnownedJsonSchemaDescriptor.class)));
    }

    @Test
    void shouldTrackUnownedJsonSchemaWhenOwnedInputConvertedToOutput() {
        // Given:
        final OwnedKafkaTopicInput<Long, String> input =
                inputTopic("name", Long.class, String.class, config);

        // When:
        final KafkaTopicOutput<Long, String> output = input.toOutput();

        // Then:
        assertThat(
                output.value().resources().toList(),
                contains(instanceOf(UnownedJsonSchemaDescriptor.class)));
    }
}
