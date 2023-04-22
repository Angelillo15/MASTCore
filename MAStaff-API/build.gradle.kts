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

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://jitpack.io")
}

publishing {
    repositories {
        maven {
            name = "MASStaff-API"
            url = uri("https://maven.pkg.github.com/Angelillo15/Angelillo15") // Github Package
            credentials {
                //Fetch these details from the properties file or from Environment variables
                username = properties.get("gpr.usr") as String? ?: System.getenv("GPR_USER")
                password = properties.get("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
            }
        }

        maven {
            name = "MASStaff-API"
            url = uri("https://maven.pkg.github.com/Nookure/MAStaff") // Github Package
            credentials {
                //Fetch these details from the properties file or from Environment variables
                username = properties.get("gpr.usr") as String? ?: System.getenv("GPR_USER")
                password = properties.get("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "es.angelillo15"
            artifactId = "mast"
            version = project.version.toString()

            from(components["java"])
        }
    }
}

dependencies {
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("com.github.mrgraycat:eGlow:master-SNAPSHOT")
    compileOnly("net.byteflux:libby-bukkit:1.1.5")
    compileOnly("com.github.hamza-cskn.obliviate-invs:core:4.1.10")
    compileOnly("com.github.hamza-cskn.obliviate-invs:configurablegui:4.1.10")
    compileOnly("com.github.hamza-cskn.obliviate-invs:advancedslot:4.1.10")
    compileOnly("com.github.hamza-cskn.obliviate-invs:pagination:4.1.10")
    compileOnly("ru.vyarus:yaml-config-updater:1.4.2")
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    compileOnly("redis.clients:jedis:4.4.0-m2")
    compileOnly("net.kyori:adventure-text-minimessage:4.13.1")
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.2")
}

blossom {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val current = LocalDateTime.now().format(formatter)


    replaceTokenIn("src/main/java/es/angelillo15/mast/api/Constants.java")
    replaceToken("{version}", project.version)
    replaceToken("{git-commit}",  grgit.head().abbreviatedId ?: "undefined")
    replaceToken("{git-user}", grgit.head().committer.name ?: "undefined")
    replaceToken("{git-date}", current ?: "undefined")
    replaceToken("{git-branch}", grgit.branch.current().name ?: "undefined")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}