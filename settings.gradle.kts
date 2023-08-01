/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.6/userguide/multi_project_builds.html
 */

rootProject.name = "MAStaff"
include("MAStaff-API")
include("MAStaff-Bukkit")
include("MAStaff-Bungee")
include("MAStaff-Legacy")
include("MAStaff-Punishments")
include("MAStaff-Punishments:Punishments-Bukkit")
findProject(":MAStaff-Punishments:Punishments-Bukkit")?.name = "Punishments-Bukkit"
include("MAStaff-Punishments:Punishments-Bungee")
findProject(":MAStaff-Punishments:Punishments-Bungee")?.name = "Punishments-Bungee"
include("MAStaff-Punishments:Punishments-API")
findProject(":MAStaff-Punishments:Punishments-API")?.name = "Punishments-API"
include("MAStaff-Vanish")
include("MAStaff-Glow")
include("MAStaff-PAPI")
include("MAStaff-Velocity")
include("MAStaff-Common")
include("MAStaff-Lite")
