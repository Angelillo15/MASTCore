plugins {
  id("net.kyori.blossom") version "2.1.0"
  alias(libs.plugins.grgit)
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  compileOnly(libs.paperApi)
  compileOnly(libs.guava)
  compileOnly(libs.apacheCommons)
  compileOnly(libs.configurateYaml)
  compileOnly(libs.jedis)
  compileOnly(libs.adventureApi)
  compileOnly(libs.miniMessage)
}

tasks.test {
  useJUnitPlatform()
}

sourceSets {
  main {
    blossom {
      javaSources {
        property("version", rootProject.version.toString())
        property("branch",  grgit.branch.current().name)
        property("commit", grgit.head().abbreviatedId)
      }
    }
  }
}