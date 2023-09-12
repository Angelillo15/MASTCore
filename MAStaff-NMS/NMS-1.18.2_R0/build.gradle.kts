plugins {
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

dependencies {
    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    shadowJar {
        archiveFileName = "${project.name}-exclude.jar"
    }
}