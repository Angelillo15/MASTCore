plugins {
    id("java")
}

group = "es.angelillo15"
version = "2.2.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")

}

dependencies {
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    compileOnly(project(":MAStaff-Punishments:Punishments-API"))
}