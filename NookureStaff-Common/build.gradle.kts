dependencies {
  compileOnly(libs.jedis)
  compileOnly(libs.hikariCP)
  compileOnly(libs.paperApi)
  compileOnly(project(":NookureStaff-API"))
}
