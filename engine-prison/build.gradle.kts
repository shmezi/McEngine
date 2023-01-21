plugins {
    id("engine-library-conventions")
    id("engine-base-conventions")

}
group = "me.alexirving.prison"

dependencies {
    implementation(project(":engine-core"))
}

tasks {
    shadowJar {
        dependencies {
            include(project(":engine-core"))
            exclude(dependency("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT"))
        }
    }
    runServer {
        dependsOn(":engine-core:shadowJar")
        minecraftVersion("1.19")
    }
}