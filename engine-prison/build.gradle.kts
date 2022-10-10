plugins {
    id("engine-library-conventions")

    id("engine-base-conventions")
}
dependencies {
    compileOnly(project(":engine-core"))
}

tasks {

    runServer {
        dependsOn(":engine-core:shadowJar")

        minecraftVersion("1.19")
    }
}