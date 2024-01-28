plugins {
  id("java")
  alias(libs.plugins.shadowJar)
}

group = "com.nookure.staff"
version = "1.0.0"

dependencies {
  implementation(project(":NookureStaff-Paper"))
  implementation(libs.configurateYaml)
  implementation(libs.guice)
  implementation(libs.storm)
  implementation(libs.hikariCP)
  implementation(libs.apacheCommons)
}

tasks.shadowJar {
  archiveFileName.set("NookureStaff-${rootProject.version}.jar")
  relocate("org.spongepowered.configurate", "com.nookure.staff.libs.configurate")
}

allprojects {
  apply<JavaPlugin>()
  apply(plugin = "com.github.johnrengelman.shadow")

  repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://maven.nookure.com")
    maven("https://mvn.exceptionflug.de/repository/exceptionflug-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
  }

  dependencies {
    compileOnly(rootProject.libs.guice)
    compileOnly(rootProject.libs.storm)
    compileOnly(rootProject.libs.adventureApi)
    compileOnly(rootProject.libs.miniMessage)
  }

  tasks {
    withType<JavaCompile> {
      options.encoding = "UTF-8"
    }

    withType<Javadoc> {
      options.encoding = "UTF-8"
    }
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}
