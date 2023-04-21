plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.github.Angelillo15:ConfigManager:1.4")
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
}
