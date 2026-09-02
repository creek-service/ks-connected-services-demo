import io.github.creek.service.ks.connected.services.demo.services.HandleOccurrenceServiceDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.connected.services.demo.services {
    requires transitive ks.connected.services.demo.api;

    exports io.github.creek.service.ks.connected.services.demo.services;

    provides ComponentDescriptor with
            io.github.creek.service.ks.connected.services.demo.services
                    .HandleOccurrenceFilteringServiceDescriptor,
            HandleOccurrenceServiceDescriptor;
}
