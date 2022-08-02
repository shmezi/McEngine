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
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0") /*STDLIB of kotlin*/
    implementation("org.litote.kmongo:kmongo:4.5.1")
    implementation("org.reflections:reflections:0.10.2")/*REFLECTIONS - REFLECTIONS UTILS*/
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")/*CoRoutines - Used for async*/
    implementation("net.kyori:adventure-text-minimessage:4.11.0") /*Adventure - Modern chat comp*/
    implementation("ch.qos.logback:logback-classic:1.2.11") /*Logging framework*/
    implementation("com.github.retrooper.packetevents:spigot:2.0.0-SNAPSHOT") /*PacketEvents - Used for handling all packet wrapping WITHOUT NMS*/
    /**
     * Frameworks
     */
    implementation("dev.triumphteam", "triumph-gui", "3.1.1")
    implementation("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT")
    implementation("de.tr7zw:item-nbt-api:2.10.0")

    implementation("com.github.shmezi:AlexLib:1.4")


    /**
     * Plugin hooks:
     */
    compileOnly("com.sk89q.worldedit", "worldedit-bukkit", "7.2.10")
    compileOnly("com.github.brcdev-minecraft:shopgui-api:2.4.0")


    implementation("org.bstats:bstats-bukkit:3.0.0")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.0")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.8.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}
tasks {
    shadowJar {
        relocate("dev.triumphteam.gui", "me.alexirving.core.libs.gui")
        relocate("me.mattstudios.mf", "me.alexirving.core.libs.mf")
        relocate("org.bstats", "me.alexirving.core.libs.bstats")
        relocate("de.tr7zw.changeme.nbtapi", "me.alexirving.core.libs.nbtapi")
        relocate("net.kyori.adventure", "me.alexirving.core.libs.minimessages")
        relocate("com.github.retrooper", "me.alexirving.core.libs.packets")
        manifest {
            attributes("Main-Class" to "me.alexirving.core.NotAProgramKt")
        }

    }
    runServer {
        minecraftVersion("1.19")
    }
}