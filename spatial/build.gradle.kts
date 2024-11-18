import java.text.SimpleDateFormat
import java.util.*

var envVersion: String = System.getenv("VERSION") ?: "0.0.1"
if (envVersion.startsWith("v"))
    envVersion = envVersion.trimStart('v')

plugins {
    id("idea")
    id("java")
    id("maven-publish")

    alias(neoforged.plugins.moddev)
}

base {
    archivesName = "spatial"
    group = "dev.compactmods"
    version = envVersion
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

neoForge {
    neoFormVersion = neoforged.versions.neoform
}

tasks.withType<Jar> {
    val gitVersion = providers.exec {
        commandLine("git", "rev-parse", "HEAD")
    }.standardOutput.asText.get()

    manifest {
        val now = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        attributes(
            mapOf(
                "Automatic-Module-Name" to "spatial",
                "Specification-Title" to "Spatial",
                "Specification-Version" to "1", // We are version 1 of ourselves
                "Implementation-Title" to "Spatial",
                "Implementation-Timestamp" to now,
                "FMLModType" to "GAMELIBRARY",
                "Minecraft-Version" to mojang.versions.minecraft.get(),
                "Commit-Hash" to gitVersion
            )
        )
    }
}

val PACKAGES_URL = System.getenv("GH_PKG_URL") ?: "https://maven.pkg.github.com/compactmods/spatial"
publishing {
    publications.register<MavenPublication>("spatial") {
        from(components.getByName("java"))
    }

    repositories {
        // GitHub Packages
        maven(PACKAGES_URL) {
            name = "GitHubPackages"
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}