package me.alexirving.core.manager

import java.util.*

/**
 * Represents an async database.
 * FYI all methods should have the prefix of db to show that this is for internal use and you should **NEVER** use it except in the userManager!
 */
interface Database {
    /**
     * Reload the database
     * @param connection The connection string to use for the database
     */
    fun dbReload(connection: String)

    /**
     * Retrieve a data from the database
     */
    fun <Db, T> dbGet(db: Db, key: UUID, async: (value: T) -> Unit)


    fun <Db, T> dbUpdate(db: Db, key: UUID, value: T)
}
