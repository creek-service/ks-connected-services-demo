module ks.connected.services.demo.api {
    requires transitive creek.kafka.metadata;

    exports io.github.creek.service.ks.connected.services.demo.api;
    exports io.github.creek.service.ks.connected.services.demo.internal to
            ks.connected.services.demo.services,
            ks.connected.services.demo.service;
}
