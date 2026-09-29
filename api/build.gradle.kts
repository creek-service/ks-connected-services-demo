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
    compileOnlyApi("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")

    compileOnly("com.github.spotbugs:spotbugs-annotations:${property("spotBugsVersion")}")

    // The module descriptor's `requires static` needs this resolvable when compiling the test module patch too.
    testCompileOnly("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")
    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")

    jsonSchemaGenerator("org.creekservice:creek-json-schema-generator:${property("creekVersion")}")
}

creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
