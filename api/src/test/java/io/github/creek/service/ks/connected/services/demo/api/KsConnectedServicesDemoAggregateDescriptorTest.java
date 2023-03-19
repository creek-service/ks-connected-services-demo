package io.github.creek.service.ks.connected.services.demo.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;
import org.creekservice.api.platform.metadata.ComponentDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptors;
import org.junit.jupiter.api.Test;

class KsConnectedServicesDemoAggregateDescriptorTest {

    @Test
    void shouldLoadDescriptor() {
        final List<ComponentDescriptor> loaded = ComponentDescriptors.load();
        assertThat(loaded, hasItem(instanceOf(KsConnectedServicesDemoAggregateDescriptor.class)));
    }
}
