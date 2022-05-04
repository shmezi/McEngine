package me.alexirving.core.db

import me.alexirving.core.channels.ChannelData
import me.alexirving.core.gangs.GangData
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
     * Retrieve a user from the database
     */
    fun dbGetUser(uuid: UUID, async: (userData: UserData) -> Unit)

    fun dbGetChannel(uuid: UUID, success: (channel: ChannelData) -> Unit, failure: () -> Unit)

    fun dbGetChannel(uuid: UUID, success: (channel: ChannelData) -> Unit) = dbGetChannel(uuid, success) {}

    fun dbUpdateChannel(channel: ChannelData)


    fun dbGetGang(uuid: UUID, success: (channel: GangData) -> Unit, failure: () -> Unit)

    fun dbDeleteGang(uuid: UUID)

    fun dbUpdateGang(gang: GangData)

    /**
     * Retrieves all the users from the database
     * @param async The function to call when the data is retrieved
     *
     */
    fun dbGetUsers(async: (userData: List<UserData>) -> Unit)

    /**
     * Change values of a user
     */
    fun dbUpdateUser(userData: UserData)

    fun dbUpdateUsers(users: List<UserData>)
}