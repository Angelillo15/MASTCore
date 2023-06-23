plugins {
    id("java")
}

group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    group = parent?.group ?: "es.angelillo15"
    version = parent?.version ?: "undefined"
    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly(project(":MAStaff-API"))
    }
}