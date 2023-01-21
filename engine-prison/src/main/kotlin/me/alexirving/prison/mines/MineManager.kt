package me.alexirving.prison.mines

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.pattern.RandomPattern
import com.sk89q.worldedit.regions.CuboidRegion
import me.alexirving.core.EngineManager
import me.alexirving.core.utils.getLocation
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener

class MineManager( val engine: EngineManager) : Listener {
    private val loadedMines = mutableMapOf<String, Mine>() //mines loaded in
    private val mineTasks = mutableMapOf<String, Int>() //scheduler task ids
    private val freeMines =
        mutableMapOf<String, MutableList<PrivateMine>>() //MineId - [ Private mines for specified id]
    private val inMine = mutableMapOf<Player, Mine>() //For faster searches duplicate list to store whos in a mine

    fun newPrivateMineSession(id: String, player: Player, success: (mine: PrivateMine) -> Unit, failure: () -> Unit) {
        val fMines = freeMines[id]
        if (fMines.isNullOrEmpty()) {
            failure()
            return
        }
        engine.user.get(player.uniqueId) {
            val mine = fMines[0]
            mine.resetMine()
            mine.join(player)
            fMines.remove(mine)
            inMine[player] = mine
            success(mine)
        }
    }


    fun unload(player: Player) {
        val mine = inMine[player] ?: return
        if (isInPrivateMine(player))
            freeMines.getOrPut(mine.id) { mutableListOf() }.add(mine as PrivateMine)
        inMine.remove(player)
    }

    fun getCurrentMine(player: Player) = inMine[player]

    fun isInPrivateMine(player: Player) = inMine[player] is PrivateMine

    fun isOwnerOfMine(player: Player) =
        if (isInPrivateMine(player))
            (getCurrentMine(player) as PrivateMine).currentOwner == player
        else false

    fun joinMineById(id: String, player: Player) {
        val m = getMine(id) ?: return
        m.join(player)
        inMine[player] = m
    }

    fun newPrivateMineSession(id: String, player: Player, success: (mine: PrivateMine) -> Unit) =
        newPrivateMineSession(id, player, success) {}


    fun isPlayerInMine(player: Player): Boolean = inMine.contains(player)

    fun isInPlayerMine(player: Player, location: Location, async: (inMine: Boolean) -> Unit) {

        getPlayerMine(player) {
            async(it?.region?.contains(BukkitAdapter.asBlockVector(location)) ?: false)
        }
    }

    fun isInMine(location: Location): Boolean {
        for (v in inMine.values)
            if (v.region.contains(BukkitAdapter.asBlockVector(location))) return true
        for (v in loadedMines.values)
            if (v.region.contains(BukkitAdapter.asBlockVector(location))) return true
        return false
    }

    fun getMine(id: String) = loadedMines[id]

    fun getPlayerMine(player: Player, async: (mine: Mine?) -> Unit) {
        engine.point.getPointTrack("PRESTIGE")?.getLevel(player.uniqueId) {
            async(getMine(it.id))
        }
    }

    init {
        reload()
    }

    fun load(mine: Mine) {
        if (mine is PrivateMine) {
            freeMines.getOrPut(mine.id) { mutableListOf() }.add(mine)

        } else loadedMines[mine.id] = mine
        mineTasks[mine.id] =
            Bukkit.getScheduler().scheduleSyncRepeatingTask(engine.engine, {
                mine.resetMine()

            }, 0L, mine.duration)
    }

    fun reload() {
        mineTasks.forEach { Bukkit.getScheduler().cancelTask(it.value) }
        mineTasks.clear()
        loadedMines.clear()
        freeMines.clear()
        inMine.clear()
        val c = engine.engine.config.getConfigurationSection("Mines")
        for (mineId in c?.getKeys(false) ?: return) {
            val mc = c.getConfigurationSection(mineId)
            val lmc = mc?.getConfigurationSection("Location")
            val w = Bukkit.getWorld(lmc?.getString("World") ?: continue) ?: continue
            load(
                Mine(
                    mineId,
                    engine,
                    mc.getLong("Duration"),
                    lmc.getLocation("Spawn", w),
                    CuboidRegion(
                        BukkitAdapter.adapt(w),
                        BukkitAdapter.asBlockVector(lmc.getLocation("LocationA", w)),
                        BukkitAdapter.asBlockVector(lmc.getLocation("LocationB", w))
                    ),
                    RandomPattern().apply {
                        val materials = mc.getConfigurationSection("Materials")
                        materials?.getKeys(false)?.forEach {
                            add(
                                BukkitAdapter.asBlockType(Material.getMaterial(it.uppercase()))?.defaultState,
                                materials.getDouble(it)
                            )
                        }
                    }, mc.getStringList("On-Reset"), mc.getStringList("On-Reset-Player")
                )
            )
            for (privateId in mc.getConfigurationSection("Private")?.getKeys(false) ?: continue) {
                val pc = c.getConfigurationSection("$mineId.Private.$privateId") ?: continue
                load(
                    PrivateMine(
                        privateId, engine,
                        pc.getLong("Duration"),
                        pc.getLocation("Spawn", w),
                        CuboidRegion(
                            BukkitAdapter.adapt(w),
                            BukkitAdapter.asBlockVector(pc.getLocation("LocationA", w)),
                            BukkitAdapter.asBlockVector(pc.getLocation("LocationB", w))
                        ),
                        RandomPattern().apply {
                            val materials = pc.getConfigurationSection("Materials")
                            materials?.getKeys(false)?.forEach {
                                add(
                                    BukkitAdapter.asBlockType(Material.getMaterial(it.uppercase()))?.defaultState,
                                    materials.getDouble(it)
                                )
                            }
                        },
                        PrisonSettings(),
                        pc.getStringList("On-Reset"),
                        mc.getStringList("On-Reset-Player")
                    )
                )
            }
        }

    }


}