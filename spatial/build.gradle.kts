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
    neoFormVersion = neoforged.versions.neoform
}