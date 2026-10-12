import io.github.creek.service.ks.connected.services.demo.services.HandleOccurrenceServiceDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.connected.services.demo.services {
    requires transitive ks.connected.services.demo.api;
    requires static creek.base.annotation;
    requires static com.fasterxml.jackson.annotation;
    requires static io.swagger.v3.oas.annotations;

    exports io.github.creek.service.ks.connected.services.demo.services;
    exports io.github.creek.service.ks.connected.services.demo.services.model;

    opens io.github.creek.service.ks.connected.services.demo.services.model;

    provides ComponentDescriptor with
            io.github.creek.service.ks.connected.services.demo.services
                    .HandleOccurrenceFilteringServiceDescriptor,
            HandleOccurrenceServiceDescriptor;
}
