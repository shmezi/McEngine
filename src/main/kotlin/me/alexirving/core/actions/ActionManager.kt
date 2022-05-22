package me.alexirving.core.actions

import me.alexirving.core.EngineManager
import me.alexirving.core.manager.LoaderManager
import org.reflections.Reflections

class ActionManager(val m: EngineManager) : LoaderManager<Action>("actionManager") {

    private val reflections = Reflections("me.alexirving.core.actions.actionables")
    private val actions: MutableSet<Class<out Action>> = reflections.getSubTypesOf(Action::class.java)
    private val superActions: MutableSet<Class<out SuperAction>> = reflections.getSubTypesOf(SuperAction::class.java)

    fun getActionClassFromName(name: String): Class<out Action> {
        return actions.filter { it.simpleName == name }[0]
    }

    fun registerAction(clazz: Class<out Action>) {
        actions.add(clazz)
    }

    fun registerSuperAction(clazz: Class<out SuperAction>) {
        superActions.add(clazz)
    }

    fun doesActionExist(name: String?): Boolean {
        name ?: return false
        return !actions.none { it.simpleName == name }
    }

    fun getSuperActionClassFromName(name: String): Class<out SuperAction> {
        return superActions.filter { it.simpleName == name }[0]
    }

    fun doesSuperActionExist(name: String?): Boolean {
        name ?: return false
        return !superActions.none { it.simpleName == name }
    }

    fun compileAction(m: EngineManager, data: Map<String, Any>): Action? {
        return getActionClassFromName(data["type"] as String? ?: return null).getDeclaredConstructor(
            EngineManager::class.java,
            Map::class.java
        ).newInstance(m, data)
    }


    fun compileSuperAction(m: EngineManager, data: Map<String, Any>, start: Int): SuperAction? {

        return getSuperActionClassFromName(data["type"] as String? ?: return null).getDeclaredConstructor(
            EngineManager::class.java,
            Map::class.java,
            Int::class.java
        ).newInstance(m, data, start)
    }
}