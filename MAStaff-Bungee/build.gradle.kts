plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    implementation(project(":MAStaff-Common"))
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    compileOnly("com.github.Angelillo15:ConfigManager:1.4")
}
