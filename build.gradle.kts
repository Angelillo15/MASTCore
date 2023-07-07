plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "4.0.4"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "es.angelillo15"
version = "2.3.2-DEV"
val javaVersion = JavaVersion.VERSION_1_8

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.shadowJar {
    relocate("es.angelillo15.configmanager", "es.angelillo15.mast.libs.config.manager")
    relocate("org.yaml.snakeyaml", "es.angelillo15.mast.libs.snakeyaml")
    relocate("org.simpleyaml", "es.angelillo15.mast.libs.simpleyaml")
    relocate("es.angelillo15.glow", "es.angelillo15.mast.libs.glow")
    relocate("mc.obliviate", "es.angelillo15.mast.libs.obliviate")
    relocate("com.zaxxer.hikari", "es.angelillo15.mast.libs.hikari")
    relocate("com.google.common", "es.angelillo15.mast.libs.google.common")
    relocate("com.google.gson", "es.angelillo15.mast.libs.google.gson")
    relocate("com.google.thirdparty", "es.angelillo15.mast.libs.google.thirdparty")
    relocate("com.google.errorprone", "es.angelillo15.mast.libs.google.errorprone")
    relocate("com.google.j2objc", "es.angelillo15.mast.libs.google.j2objc")
    relocate("javax.annotation", "es.angelillo15.mast.libs.javax.annotation")
    relocate("org.checkerframework", "es.angelillo15.mast.libs.checkerframework")
    relocate("net.byteflux.libby", "es.angelillo15.mast.libs.libby")
    relocate("ru.vyarus.yaml.updater", "es.angelillo15.mast.libs.yaml-config-updater")
    relocate("kong.unirest", "es.angelillo15.mast.libs.unirest")
    relocate("org.apache.http", "es.angelillo15.mast.libs.apache.http")
    relocate("org.apache.commons.logging", "es.angelillo15.mast.libs.commons-logging")
    relocate("org.reflections", "es.angelillo15.mast.libs.reflections")
    relocate("redis.clients.jedis", "es.angelillo15.mast.libs.jedis")
    relocate("net.kyori", "es.angelillo15.mast.libs.kyori")
    relocate("io.papermc.lib", "es.angelillo15.mast.libs.paperlib")
    relocate("com.github.benmanes.caffeine", "es.angelillo15.mast.libs.caffeine")
    relocate("com.craftmend.storm", "es.angelillo15.mast.libs.storm")
}

dependencies {
    implementation(project(":MAStaff-API"))
    implementation(project(":MAStaff-Bukkit"))
    implementation(project(":MAStaff-Bungee"))
    implementation(project("MAStaff-Legacy"))
    implementation(project(":MAStaff-Punishments"))
    implementation(project(":MAStaff-Vanish"))
    implementation(project(":MAStaff-PAPI"))
    implementation(project(":MAStaff-Glow"))
    implementation(libs.simpleYaml)
    implementation(libs.configManager)
    implementation(libs.bundles.invAPI)
    implementation(libs.gson)
    implementation(libs.liblyBukkit)
    implementation(libs.reflections)
    implementation(libs.paperLib)
    implementation(libs.caffeine)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveFileName.set("MAStaff-" + project.version + ".jar")
}

allprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://jitpack.io")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://repo.alessiodp.com/releases/")
        maven("https://papermc.io/repo/repository/maven-releases/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        compileOnly("org.reflections:reflections:0.10.2")
    }
}