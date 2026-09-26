import io.github.creek.service.ks.connected.services.demo.api.KsConnectedServicesDemoAggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.connected.services.demo.api {
    requires transitive creek.kafka.metadata;
    requires creek.base.annotation;
    requires static com.github.spotbugs.annotations;

    exports io.github.creek.service.ks.connected.services.demo.api;
    exports io.github.creek.service.ks.connected.services.demo.api.model;
    exports io.github.creek.service.ks.connected.services.demo.internal to
            ks.connected.services.demo.services,
            ks.connected.services.demo.service;

    // Required so Jackson (used by the JSON serde) can reflectively access the record's canonical
    // constructor and component accessors at runtime.
    opens io.github.creek.service.ks.connected.services.demo.api.model;

    provides ComponentDescriptor with
            KsConnectedServicesDemoAggregateDescriptor;
}
