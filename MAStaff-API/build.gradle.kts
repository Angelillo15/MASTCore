import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
  id("java")
  `maven-publish`
  id("net.kyori.blossom") version "1.3.1"
  id("org.ajoberstar.grgit") version "4.1.0"
}

group = "es.angelillo15"
version = parent?.version ?: "2.0.0"

val compileOnlyApi = configurations.maybeCreate("compileOnlyApi")
configurations.getByName(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME).extendsFrom(compileOnlyApi);
configurations.getByName(JavaPlugin.API_ELEMENTS_CONFIGURATION_NAME).extendsFrom(compileOnlyApi);

dependencies {
  compileOnly(libs.waterfall)
  compileOnly(libs.paperApi)
  compileOnly(libs.placeholderApi)
  compileOnly(libs.vault)
  compileOnly(libs.velocity)
  compileOnlyApi(libs.liblyBukkit)
  compileOnlyApi(libs.bundles.invAPI)
  compileOnlyApi(libs.configUpdater)
  compileOnlyApi(libs.snakeYaml)
  compileOnlyApi(libs.simpleYaml)
  compileOnlyApi(libs.jedis)
  compileOnlyApi(libs.hikariCP)
  compileOnlyApi(libs.caffeine)
  compileOnlyApi(libs.storm)
  compileOnlyApi(libs.configManager)
  compileOnlyApi(libs.adventureApi)
  compileOnlyApi(libs.adventureBukkit)
  compileOnlyApi(libs.adventureBungee)
  compileOnlyApi(libs.miniMessage)
  compileOnlyApi(libs.kotlin)
  compileOnlyApi(libs.reflections)
  compileOnlyApi(libs.guice)
  apiElements(libs.guice)

  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation(project(":MAStaff-Common"))
  testImplementation(libs.guice)
  testImplementation(libs.kotlin)
  testImplementation(libs.reflections)
}

tasks.test {
  useJUnitPlatform()
}

blossom {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
  val current = LocalDateTime.now().format(formatter)


  replaceTokenIn("src/main/java/es/angelillo15/mast/api/Constants.java")
  replaceToken("{version}", project.version)
  replaceToken("{git-commit}", grgit.head().abbreviatedId ?: "undefined")
  replaceToken("{git-user}", grgit.head().committer.name ?: "undefined")
  replaceToken("{git-date}", current ?: "undefined")
  replaceToken("{git-branch}", grgit.branch.current().name ?: "undefined")

  if (project.version.toString().endsWith("-SNAPSHOT") ||
          project.version.toString().endsWith("-DEV") ||
          project.version.toString().endsWith("-BETA") ||
          project.version.toString().endsWith("-ALPHA")
  ) {
    replaceToken("false", "true")
  }
}

tasks.withType<Javadoc> {
  options.encoding = "UTF-8"
}

publishing {
  repositories {
    maven {
      name = "nookureRepository"
      url = uri("https://repo.nookure.com/releases")
      credentials(PasswordCredentials::class)
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }

  publications {
    create<MavenPublication>("maven") {
      groupId = "com.nookure.mast"
      artifactId = "MAStaff-API"
      version = "${rootProject.version}"
      from(components["java"])
    }
  }
}