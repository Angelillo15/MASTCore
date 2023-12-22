import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  `maven-publish`
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("org.ajoberstar.grgit") version "5.2.1"
  id("org.ajoberstar.git-publish") version "4.2.1"
}

dependencies {
  implementation(libs.liblyBukkit)
  implementation(libs.bundles.invAPI)
  implementation(libs.configUpdater)
  implementation(libs.snakeYaml)
  implementation(libs.simpleYaml)
  implementation(libs.jedis)
  implementation(libs.hikariCP)
  implementation(libs.caffeine)
  implementation(libs.storm)
  implementation(libs.configManager)
  implementation(libs.adventureApi)
  implementation(libs.adventureBukkit)
  implementation(libs.adventureBungee)
  implementation(libs.miniMessage)
  implementation(libs.kotlin)
  implementation(libs.reflections)
  implementation(libs.guice)
  implementation(project(":MAStaff-API"))
}

publishing {
  repositories {
    maven {
      name = "local"
      url = uri("${rootProject.rootDir}/maven")
    }
  }

  publications {
    create<MavenPublication>("maven") {
      groupId = "com.nookure.mast"
      artifactId = "MAStaff-API"
      version = "${rootProject.version}"
      artifact(tasks.withType(ShadowJar::class.java).getByName("shadowJar").archiveFile)
    }
  }
}

tasks.shadowJar {
  Relocation.registerAll()
  Relocation.relocations.forEach { (from, to) ->
    relocate(from, to)
  }
  exclude("META-INF/maven/**")
}

gitPublish {
  repoUri = "https://github.com/Nookure/maven.git"
  branch = "main"
  fetchDepth = null
  commitMessage = "MAStaff-API ${parent!!.version}"

  contents {
    from("${rootProject.rootDir}/maven")
  }

  preserve {
    include("**")
  }
}