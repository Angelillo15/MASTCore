dependencies {
  compileOnly(libs.jedis)
  compileOnly(libs.hikariCP)
  compileOnly(libs.paperApi)
  compileOnly(libs.ebean.ddl.generator)
  compileOnly(libs.ebean)
  compileOnly(libs.ebean.migration)
  compileOnly(libs.flyway)
  compileOnly(project(":NookureStaff-API"))
}
