plugins {
    kotlin("jvm") version "1.9.20" // Use Kotlin for JVM
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    application
}

repositories {
    mavenCentral()
}

val kafkaVersion = "3.8.0" // Update to the latest stable Kafka version if needed
val jacksonVersion = "2.17.2"

dependencies {
    compileOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // Kafka Streams core library
    implementation("org.apache.kafka:kafka-streams:$kafkaVersion")

    // Kafka clients (required for producer/consumer functionality)
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")

    // Jackson for JSON serialization/deserialization (optional)
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    // Logging dependencies (optional but recommended)
    //implementation("org.slf4j:slf4j-api:2.0.9")
    //implementation("ch.qos.logback:logback-classic:1.4.11")

    // Testing dependencies
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.example.Main")
}

tasks.test {
    useJUnitPlatform()
}
