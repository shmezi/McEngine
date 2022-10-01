rootProject.name = "mc-engine"

include("engine-core", "engine-prison")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}