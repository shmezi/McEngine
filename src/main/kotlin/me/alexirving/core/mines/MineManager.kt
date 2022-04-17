package me.alexirving.core.mines

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.pattern.RandomPattern
import com.sk89q.worldedit.regions.CuboidRegion
import me.alexirving.core.EngineManager
import me.alexirving.core.utils.getLocation
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.*

class MineManager(val m: EngineManager) : Listener {
    private val loadedMines = mutableMapOf<String, Mine>()
    private val mineTasks = mutableMapOf<String, Int>()
    private val freeMines = mutableMapOf<String, MutableList<PrivateMine>>()
    private val inuse = mutableMapOf<UUID, PrivateMine>()

    fun newPrivateMineSession(id: String, player: Player, success: (mine: PrivateMine) -> Unit, failure: () -> Unit) {
        val fMines = freeMines[id]
        if (fMines.isNullOrEmpty()) {
            failure()
            return
        }
        m.database.getUser(player.uniqueId) {
            val mine = fMines[0]
            mine.join(player)
            fMines.remove(mine)
            mine.settings = it.settings
            inuse[player.uniqueId] = mine
            success(mine)
        }
    }

    fun newPrivateMineSession(id: String, player: Player, success: (mine: PrivateMine) -> Unit) =
        newPrivateMineSession(id, player, success) {}

    fun getMine(id: String) = loadedMines[id]

    init {
        reload()
    }

    fun load(mine: Mine) {
        if (mine is PrivateMine) {
            freeMines.getOrPut(mine.id) { mutableListOf() }.add(mine)

        } else loadedMines[mine.id] = mine
        mineTasks[mine.id] =
            Bukkit.getScheduler().scheduleSyncRepeatingTask(m.engine, {
                mine.resetMine()
                //TODO: REMEMBER TO TP PLAYER BACK TO TOP
            }, 0L, mine.duration)
    }

    fun reload() {
        mineTasks.forEach { Bukkit.getScheduler().cancelTask(it.value) }
        mineTasks.clear()
        loadedMines.clear()
        freeMines.clear()
        inuse.clear()
        val c = m.engine.config.getConfigurationSection("Mines")
        c.getKeys(false).forEach { mineId ->
            val mc = c.getConfigurationSection(mineId)
            val lmc = mc.getConfigurationSection("Location")
            val w = Bukkit.getWorld(lmc.getString("World"))
            load(
                Mine(
                    mineId,
                    m,
                    mc.getLong("Duration"),
                    lmc.getLocation("Spawn", w),
                    CuboidRegion(
                        BukkitAdapter.adapt(w),
                        BukkitAdapter.asBlockVector(lmc.getLocation("LocationA", w)),
                        BukkitAdapter.asBlockVector(lmc.getLocation("LocationB", w))
                    ),
                    RandomPattern().apply {
                        val materials = mc.getConfigurationSection("Materials")
                        materials.getKeys(false).forEach {
                            add(
                                BukkitAdapter.asBlockType(Material.getMaterial(it.uppercase()))?.defaultState,
                                materials.getDouble(it)
                            )
                        }
                    })
            )
            mc.getConfigurationSection("Private").getKeys(false).forEach { privateId ->
                val pc = c.getConfigurationSection("$mineId.Private.$privateId")
                load(
                    PrivateMine(
                        privateId,
                        m,
                        pc.getLong("Duration"),
                        pc.getLocation("Spawn", w),
                        CuboidRegion(
                            BukkitAdapter.adapt(w),
                            BukkitAdapter.asBlockVector(pc.getLocation("LocationA", w)),
                            BukkitAdapter.asBlockVector(pc.getLocation("LocationB", w))
                        ),
                        RandomPattern().apply {
                            val materials = pc.getConfigurationSection("Materials")
                            materials.getKeys(false).forEach {
                                add(
                                    BukkitAdapter.asBlockType(Material.getMaterial(it.uppercase()))?.defaultState,
                                    materials.getDouble(it)
                                )
                            }
                        }, PrivateMineSettings.default()
                    )
                )
            }
        }

    }


}