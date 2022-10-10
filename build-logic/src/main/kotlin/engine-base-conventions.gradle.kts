import gradle.kotlin.dsl.accessors._889a49815d7b5aa8c7375876ff7f4048.shadowJar

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow")
    id("xyz.jpenilla.run-paper")
}
val buildGroups = "Engine Build"
tasks {
    named<TaskReportTask>("tasks") {
        displayGroup = buildGroups
    }

    tasks {
        clean {
            group = buildGroups
        }
        shadowJar {
            group = buildGroups
        }
    }


}
