repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(libs.spigot)
    compileOnly(libs.placeholderApi)
}