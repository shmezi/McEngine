
plugins {
    `java-library`
    id("com.github.johnrengelman.shadow")
    id("xyz.jpenilla.run-paper")
    kotlin("jvm")
}
val buildGroup = "Engine Tasks"
tasks {
    named<TaskReportTask>("tasks") {
        displayGroup = buildGroup
    }
    tasks.all {
        this.group = "other"
    }
    listOf<TaskProvider<*>>(clean, shadowJar, runServer)
        .forEach { it { this.group = buildGroup } }


}
