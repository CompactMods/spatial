plugins {
    id("idea")
    id("java")

    alias(neoforged.plugins.moddev)
}

base {
    archivesName = "spatial"
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
            modSourceSets.add(sourceSets.main)
        }

        create("spatialtest") {
            modSourceSets.add(sourceSets.main)
            modSourceSets.add(sourceSets.test)
        }
    }

    unitTest {
        enable()
        testedMod = mods.named("spatialtest")
    }

    runs {
        create("gameTestServer") {
            this.type = "gameTestServer"
            gameDirectory.set(file("runs/gametest"))

            systemProperty("neoforge.enabledGameTestNamespaces", "spatialtest")

            this.sourceSet = sourceSets.test
        }
    }
}

dependencies {
    testImplementation(neoforged.testframework)
}