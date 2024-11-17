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
    version = "0.1.0"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

neoForge {
    neoFormVersion = neoforged.versions.neoform
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