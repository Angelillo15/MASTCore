plugins {
    id("java")
}

repositories {
    mavenCentral()
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly(libs.protocolLib)
}