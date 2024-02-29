plugins {
    id("java")
    id("net.neoforged.gradle.userdev") version "7.0.96"
}

base {
    archivesName = "spatial"
    group = "dev.compactmods"
    version = "0.1.0"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

runs.create("gameTestServer") {
    this.gameTest()
    systemProperty("forge.enabledGameTestNamespaces", "spatial")
    modSource(sourceSets.main.get())
}

dependencies {
    // compileOnly("net.minecraft:neoform_joined:1.20.4-20231207.154220")

    implementation(libraries.neoforge)
    testImplementation(libraries.neoforge)
}