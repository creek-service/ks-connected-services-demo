module ks.connected.services.demo.service {
    requires ks.connected.services.demo.services;
    requires creek.service.context;
    requires creek.kafka.streams.extension;
    requires org.apache.logging.log4j;
    // Required so unit tests can reference JsonSerdeExtensionOptions, as unit tests are compiled
    // as part of this module, rather than being resolvable via the module path at runtime alone:
    requires creek.kafka.serde.json.schema;
}
