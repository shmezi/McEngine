package me.alexirving.core.mines

import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import me.alexirving.core.EngineManager
import org.bukkit.Location

class PrivateMine(
    id: String,
    m: EngineManager,
    duration: Long,
    spawn: Location,
    region: CuboidRegion,
    pattern: Pattern,
    var settings: PrivateMineSettings
) : Mine(
    id, m, duration, spawn,
    region, pattern
)