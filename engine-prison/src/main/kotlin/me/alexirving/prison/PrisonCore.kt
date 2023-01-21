package me.alexirving.prison

import me.alexirving.core.McEngine
import me.alexirving.prison.cmd.CMDMine
import me.alexirving.prison.mines.MineManager
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class PrisonCore : JavaPlugin(), Listener {

    private var engine: McEngine? = null
    override fun onLoad() {
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        engine = (server.pluginManager.getPlugin("McEngine") as McEngine)

        engine?.registerCommand(CMDMine(MineManager((engine?.manager ?: return))))
    }

}