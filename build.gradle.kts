plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("xyz.jpenilla.run-paper") version "1.0.6"

}

group = "me.alexirving.core"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://maven.enginehub.org/repo")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    compileOnly("org.spigotmc", "spigot-api", "1.8.8-R0.1-SNAPSHOT")
    implementation("dev.triumphteam", "triumph-gui", "3.1.1")
    implementation("org.reflections:reflections:0.10.2")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.7.0")
    implementation("ch.qos.logback:logback-classic:1.2.10")

    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.0")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.7.0")
    implementation("com.mojang:authlib:1.5.21")
}
tasks {
    shadowJar {
        relocate("dev.triumphteam.gui", "me.alexirving.core.gui")
    }
    runServer {
        minecraftVersion("1.8.8")
    }
}