plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    // Spring Boot plugin
    // Java plugin
    java

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.50.0"
}

repositories {
    // Usa Maven Central per risolvere le dipendenze.
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("mysql:mysql-connector-java:8.0.26") // Specifica la versione del driver MySQL

    // Usa JUnit Jupiter per il testing.

    // Questa dipendenza è utilizzata dall'applicazione.
    implementation("com.google.guava:guava:30.1.1-jre")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Definisci la classe principale per l'applicazione.
    mainClass.set("unibo.cinemamanager.CinemaManagerApplication")
}

tasks.named<Test>("test") {
    // Usa JUnit Platform per i test unitari.
    useJUnitPlatform()
}