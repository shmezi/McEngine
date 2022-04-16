package me.alexirving.core.db

import java.util.*

/**
 * Represents an async database.
 */
interface Database {
    /**
     * Reload the database
     * @param connection The connection string to use for the database
     */
    fun reload(connection: String)

    /**
     * Retrieve a user from the database
     */
    fun getUser(uuid: UUID, async: (userData: UserData) -> Unit)

    /**
     * Retrieves all the users from the database
     * @param async The function to call when the data is retrieved
     *
     */
    fun getUsers(async: (userData: List<UserData>) -> Unit)

    /**
     * Change values of a user
     */
    fun updateUser(userData: UserData)
}