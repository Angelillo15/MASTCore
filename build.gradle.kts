plugins {
  id("java")
  alias(libs.plugins.shadowJar)
  alias(libs.plugins.grgit)
}

group = "com.nookure.staff"
val versionCode = "1.0.0"

version = "${versionCode}-${grgit.branch.current().name.replace("/", "-")}-${grgit.head().abbreviatedId}"

if (System.getenv("nookure_staff_version") != null) {
  version = "${versionCode}-dev"
}

dependencies {
  implementation(project(":NookureStaff-Paper"))
  implementation(project(":NookureStaff-Velocity"))
  implementation(libs.liblyBukkit)
  implementation(libs.libbyPaper)
  implementation(libs.libbyVelocity)
  implementation(libs.guice)
  implementation(libs.configurateYaml)
}

tasks.shadowJar {
  archiveFileName.set("NookureStaff-${rootProject.version}.jar")
  relocate("com.zaxxer", "com.nookure.staff.libs")
  relocate("com.craftmend.storm", "com.nookure.staff.libs.storm")
  relocate("com.github.benmanes.caffeine", "com.nookure.staff.libs.caffeine")
  relocate("org.spongepowered.configurate", "com.nookure.staff.libs.configurate")
  relocate("com.alessiodp.libby", "com.nookure.staff.libs.libby")
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
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
  }

  dependencies {
    compileOnly(rootProject.libs.guice)
    compileOnly(rootProject.libs.storm)
    compileOnly(rootProject.libs.adventureApi)
    compileOnly(rootProject.libs.miniMessage)
    compileOnly(rootProject.libs.caffeine)
    compileOnly(rootProject.libs.liblyBukkit)
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
