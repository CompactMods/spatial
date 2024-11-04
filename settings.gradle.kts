rootProject.name = "spatial"

dependencyResolutionManagement {
    versionCatalogs.create("neoforged") {
        version("neoform", "1.21.1-20240808.144430")
        version("mdg", "2.0.42-beta")
        version("neoforge", "21.1.73")
        version("neoforgeRange") {
            require("[21.1, 21.2)")
            prefer("21.1.73")
        }

        plugin("moddev", "net.neoforged.moddev")
            .versionRef("mdg")

        library("neoforge", "net.neoforged", "neoforge")
            .versionRef("neoforge")

        library("testframework", "net.neoforged", "testframework")
            .versionRef("neoforge")
    }

    versionCatalogs.create("mojang") {
        version("minecraft", "1.21.1")
        version("minecraftRange") {
            this.require("[1.21, 1.21.2)")
            this.prefer("1.21.1")
        }
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

include("spatial")
// include("spatial-neoforge")