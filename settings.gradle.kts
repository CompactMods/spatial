rootProject.name = "spatial"

dependencyResolutionManagement {
    versionCatalogs.create("libraries") {
        library("neoforge", "net.neoforged", "neoforge")
                .versionRef("neoforge")

        version("minecraft", "1.20.4")
        version("neoforge", "20.4.189")
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()

        maven("https://libraries.minecraft.net") {
            name = "Minecraft"
        }

        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
            content {
                includeGroup("org.parchmentmc.data")
            }
        }

        maven("https://maven.neoforged.net/releases") {
            name = "NeoForged"
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

