group = "es.angelillo15"
version = "2.2.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")

}

dependencies {
    compileOnly(libs.waterfall)
    compileOnly(project(":MAStaff-Punishments:Punishments-API"))
}
