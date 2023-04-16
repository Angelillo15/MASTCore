plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18))
    }
}