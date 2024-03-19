dependencies {
  implementation(project(":NookureStaff-API"))
  implementation(project(":NookureStaff-Common"))
  compileOnly(libs.velocity)
  annotationProcessor(libs.velocity)
}