package me.alexirving.core.item

class Group(val name: String) {
    private val allowed = mutableSetOf<String>() //The allowed modifier IDs for this group.
    private var max = 0 //The maximum amount of modifiers for this group.
    private val modifiers = mutableMapOf<String, Modifier>() //The currently applied modifiers.


    fun addAllowed(vararg allowed: String) {
        this.allowed.addAll(allowed)
    }
    fun removeAllowed(vararg disallowed: String) {
        this.allowed.removeAll(disallowed.toSet())
    }
    fun addModifier(modifier: Modifier): Boolean {
        return if (allowed.contains(modifier.id) && max >= allowed.size) {
            modifiers[modifier.id] = modifier
            true
        } else
            false
    }

    fun removeModifier(modifier: Modifier) {
        modifiers.remove(modifier.id)
    }

    fun levelUpModifier(modifier: Modifier) {
        modifiers[modifier.id]?.levelUp()
    }

    fun levelDownModifier(modifier: Modifier) {
        modifiers[modifier.id]?.levelDown()
    }

    fun setLevelModifier(modifier: Modifier, level: Int) {
        modifiers[modifier.id]?.setLevel(level)
    }

    fun getModifier(id: String): Modifier? {
        return modifiers[id]
    }

    fun getModifiers(): MutableMap<String, Modifier> {
        return modifiers
    }


}