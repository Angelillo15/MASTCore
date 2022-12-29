plugins {
    id("java")
}

group = "es.angelillo15"
version = "2.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly(project(":MAStaff-API"))
}
