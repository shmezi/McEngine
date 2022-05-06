package me.alexirving.core.database

import java.util.*

/**
 * Represents an async database.
 * FYI all methods should have the prefix of db to show that this is for internal use and you should **NEVER** use it except in the userManager!
 */
interface Database<T : Cacheable> {
    /**
     * Reload the database
     * @param connection The connection string to use for the database
     */
    fun dbReload(connection: String)

    /**
     * Retrieve a data from the database
     */
    fun dbGet(key: UUID, async: (value: T?) -> Unit)


    fun dbUpdate(value: T)

    fun dbDelete(key: UUID)
}
