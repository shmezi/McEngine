/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * build.gradle.kts - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
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
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("org.litote.kmongo:kmongo:4.5.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha14")
    compileOnly("org.spigotmc", "spigot-api", "1.8.8-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    implementation("org.litote.kmongo:kmongo-coroutine:4.5.0")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    /**
     * Frameworks
     */
    implementation("dev.triumphteam", "triumph-gui", "3.1.1")
    compileOnly("me.mattstudios.utils:matt-framework:1.4.6")
    implementation("de.tr7zw:item-nbt-api:2.9.2")

    /**
     * Plugin hooks:
     */
    compileOnly("com.sk89q.worldedit", "worldedit-bukkit", "7.2.10")
    compileOnly("com.sk89q.worldedit", "worldedit-core", "7.2.10")

    implementation("org.bstats:bstats-bukkit:3.0.0")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.0")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.7.0")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.7.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}
tasks {
    shadowJar {
        relocate("dev.triumphteam.gui", "me.alexirving.core.gui")
        relocate("org.bstats", "me.alexirving.core.bstats")
        relocate("de.tr7zw.changeme.nbtapi", "me.alexirving.core.nbtapi")
        relocate("net.kyori.adventure", "me.alexirving.core.minimessages")
    }
    runServer {
        minecraftVersion("1.18.2")
    }
}