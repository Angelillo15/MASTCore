repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(project(":MAStaff-PAPI"))
    compileOnly(libs.spigot)
    compileOnly(libs.placeholderApi)
    compileOnly(libs.vault)
}
