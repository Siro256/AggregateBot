import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "dev.siro256.discordbot"
version = "1.0.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.siro256.dev/repository/maven-public/") }
}

dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))
    api("net.dv8tion:JDA:4.3.0_298")
    api("dev.siro256:kotlin-consolelib:1.1.0-SNAPSHOT")
    api("dev.siro256:kotlin-eventlib:1.0.0")
    api("org.yaml:snakeyaml:1.29")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<ProcessResources> {
        filteringCharset = "UTF-8"
        from(projectDir) { include("LICENSE") }
    }

    withType<Jar> {
        from(configurations.api.get().apply{ isCanBeResolved = true }.map { if (it.isDirectory) it else zipTree(it) })
        manifest.attributes("Main-Class" to "dev.siro256.discordbot.aggregatebot.AggregateBot")
        duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.EXCLUDE
    }
}
