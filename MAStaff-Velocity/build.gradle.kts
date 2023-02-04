plugins {
    java
    id("net.kyori.blossom") version "1.3.1"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

version = parent?.version ?: "unknown"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        content {
            includeGroup("com.velocitypowered")
        }
    }
    mavenCentral()
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

blossom {
    replaceTokenIn("src/main/java/es/angelillo15/mast/velocity/Constants.java")
    replaceToken("{version}", project.version)
}

