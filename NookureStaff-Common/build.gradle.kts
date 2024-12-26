dependencies {
  compileOnly(libs.jedis)
  compileOnly(libs.hikariCP)
  compileOnly(libs.paperApi)
  compileOnly(libs.ebean.ddl.generator)
  compileOnly(libs.ebean)
  compileOnly(libs.ebean.migration)
  compileOnly(project(":NookureStaff-API"))

  // Ebean migration generation dependencies
  compileOnly("com.sun.xml.bind:jaxb-impl:4.0.5")
  compileOnly("org.slf4j:slf4j-simple:2.0.13")

  testImplementation(libs.jedis)
  testImplementation(project(":NookureStaff-API"))
}

tasks.register<JavaExec>("generateMigration") {
  mainClass = "com.nookure.staff.database.MigrationService"
  classpath(sourceSets.main.get().runtimeClasspath, sourceSets.main.get().compileClasspath)
  args = listOf()
}
