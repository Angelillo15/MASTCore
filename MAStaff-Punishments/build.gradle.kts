group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project("Punishments-API"))
    implementation(project("Punishments-Bungee"))
    implementation(project("Punishments-Bukkit"))
    implementation(project("Punishments-Velocity"))

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
        compileOnly(rootProject.libs.caffeine)
        compileOnly(rootProject.libs.storm)
    }
}
