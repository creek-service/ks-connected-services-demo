plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")
    implementation("org.creekservice:creek-base-annotation:${property("creekVersion")}")
    // Used to control the fidelity of generated JSON schemas, e.g. marking non-primitive
    // record components as required:
    api("com.fasterxml.jackson.core:jackson-annotations:${property("jacksonAnnotationsVersion")}")
    // Used to annotate schema constraints (e.g. minLength/minimum) for `generateJsonSchema`.
    // `compileOnlyApi` keeps it off the runtime classpath (`creek-kafka-json-serde`'s Confluent
    // schema-registry client transitively pulls in `swagger-annotations-jakarta`, which claims the
    // same JPMS module name) while still exposing the annotation types to downstream compile
    // classpaths, avoiding "class file for ... Schema not found" warnings when they compile against
    // this module's jar.
    compileOnlyApi("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")

    compileOnly("com.github.spotbugs:spotbugs-annotations:${property("spotBugsVersion")}")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    // The module descriptor's `requires static` needs this resolvable when compiling the test module patch too.
    testCompileOnly("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")
    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")

    jsonSchemaGenerator("org.creekservice:creek-json-schema-generator:${property("creekVersion")}")
}

creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
