plugins {
    id("idea")
    id("java")

    alias(neoforged.plugins.moddev)
}

val spatialLib = project(":spatial")
project.evaluationDependsOn(spatialLib.path)

base {
    archivesName = "spatial-neoforge"
    group = "dev.compactmods"
    version = "0.1.0"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

neoForge {
    version = neoforged.versions.neoforge

    mods {
        create("spatial") {
            // modSourceSets.add(spatialLib.sourceSets.main)
        }

        create("spatial_test") {
            modSourceSets.add(sourceSets.test)
        }
    }

    unitTest {
        enable()
        testedMod = mods.named("spatial_test")
    }

    runs {
        create("gameTestServer") {
            this.type = "gameTestServer"
            gameDirectory.set(file("runs/gametest"))

            systemProperty("neoforge.enabledGameTestNamespaces", "spatial_test")

            this.sourceSet = sourceSets.test
        }
    }
}

dependencies {
    testCompileOnly(spatialLib)
    additionalRuntimeClasspath(spatialLib)

    testImplementation(neoforged.testframework)

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties: Map<String, Any> = mapOf(
        "minecraft_version" to mojang.versions.minecraft.get(),
        "neo_version_range" to neoforged.versions.neoforgeRange.get(),
        "minecraft_version_range" to mojang.versions.minecraftRange.get(),
        "loader_version_range" to "[1,)"
    )

    inputs.properties(replaceProperties)
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}