plugins {
    id("engine-library-conventions")
    id("engine-base-conventions")
}
tasks {
    shadowJar {
        doLast{
            copy {
                from(File(rootDir, "/engine-core/build/libs/engine-core-all.jar"))
                into(File(rootDir, "/engine-prison/run/plugins"))
                duplicatesStrategy = DuplicatesStrategy.INCLUDE
            }
        }
    }


    runServer {
        minecraftVersion("1.19")
    }
}