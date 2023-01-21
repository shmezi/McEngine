package me.alexirving.core.actions.data

open class ActionData {
    protected val data = mutableMapOf<String, ActionDataValue>()
    operator fun set(id: String, obj: Any): ActionData {
        data[id] = ActionDataValue(obj)
        return this
    }

    operator fun get(id: String) = data[id]
}