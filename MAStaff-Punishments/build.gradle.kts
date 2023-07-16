group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":MAStaff-Punishments:Punishments-API"))
    implementation(project(":MAStaff-Punishments:Punishments-Bungee"))
    implementation(project(":MAStaff-Punishments:Punishments-Bukkit"))
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
