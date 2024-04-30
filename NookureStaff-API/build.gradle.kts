plugins {
  id("net.kyori.blossom") version "2.1.0"
  alias(libs.plugins.grgit)
  alias(libs.plugins.grgitPublish)
  id("java")
  id("java-library")
  id("maven-publish")
}

java {
  withJavadocJar()
  withSourcesJar()

  sourceSets["main"].java {
    srcDir("src/ap/java")
  }
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  compileOnlyApi(libs.paperApi)
  compileOnlyApi(libs.guava)
  compileOnlyApi(libs.guice)
  compileOnlyApi(libs.apacheCommons)
  implementation(libs.configurateYaml)
  compileOnlyApi(libs.jedis)
  compileOnlyApi(libs.adventureApi)
  compileOnlyApi(libs.miniMessage)
  compileOnlyApi(libs.reflections)
  compileOnlyApi(rootProject.libs.storm)
  compileOnlyApi(rootProject.libs.adventureApi)
  compileOnlyApi(rootProject.libs.miniMessage)
  compileOnlyApi(rootProject.libs.caffeine)
  compileOnlyApi(rootProject.libs.liblyBukkit)

  compileOnly(libs.auto.service.annotations)
  annotationProcessor(libs.auto.service)
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
      groupId = "com.nookure.staff"
      artifactId = "NookureStaff-API"
      version = "${rootProject.version}"
      from(components["java"])
    }
  }
}

gitPublish {
  repoUri = "https://github.com/Nookure/maven.git"
  branch = "main"
  fetchDepth = null
  commitMessage = "NookureStaff ${rootProject.version}"

  contents {
    from("${rootProject.rootDir}/maven")
  }

  preserve {
    include("**")
  }
}

tasks.test {
  useJUnitPlatform()
}

tasks {
  withType<Javadoc> {
    val o = options as StandardJavadocDocletOptions
    o.encoding = "UTF-8"
    o.source = "17"

    o.links(
        "https://guava.dev/releases/${libs.guava.get().version}/api/docs/",
        "https://google.github.io/guice/api-docs/${libs.guice.get().version}/javadoc/",
        "https://docs.oracle.com/en/java/javase/17/docs/api/",
        "https://jd.advntr.dev/api/${libs.adventureApi.get().version}/",
        "https://javadoc.io/doc/com.github.ben-manes.caffeine/caffeine"
    )
  }
}

sourceSets {
  main {
    blossom {
      javaSources {
        property("version", rootProject.version.toString())
        property("branch", grgit.branch.current().name)
        property("commit", grgit.head().abbreviatedId)
      }
    }
  }
}