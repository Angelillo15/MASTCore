import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
  id("java")
  id("net.kyori.blossom") version "1.3.1"
  id("org.ajoberstar.grgit") version "5.2.1"
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
  compileOnly(libs.liblyBukkit)
  compileOnly(libs.bundles.invAPI)
  compileOnly(libs.configUpdater)
  compileOnly(libs.snakeYaml)
  compileOnly(libs.simpleYaml)
  compileOnly(libs.jedis)
  compileOnly(libs.hikariCP)
  compileOnly(libs.caffeine)
  compileOnly(libs.storm)
  compileOnly(libs.configManager)
  compileOnly(libs.adventureApi)
  compileOnly(libs.adventureBukkit)
  compileOnly(libs.adventureBungee)
  compileOnly(libs.miniMessage)
  compileOnly(libs.kotlin)
  compileOnly(libs.reflections)
  compileOnly(libs.guice)
  compileOnly(libs.configurateGson)
  compileOnly(libs.configurateHocon)
  compileOnly(libs.configurateYaml)

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
