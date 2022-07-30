package me.alexirving.core.channels

import me.alexirving.core.exceptions.NotFoundException
import me.alexirving.lib.database.Cacheable
import java.util.*

/**
 * Represents data stored about a channel
 * @param identifier the uuid used for mongoDb.
 * @param name User visible name for the channel
 * @param label The label of the channel that could be used in for example a /channel <label> command
 * @param groups The groups with permissions in the channel. [Group] handle prefixes permissions and more
 * @param default The group id that will be used for new members.
 * @param participants A Map detailing each member what group they are in
 */
class ChannelData(
    identifier: UUID,
    var name: String,
    var label: String,
    val groups: MutableList<Group>,
    var default: String,
    var owner: UUID?,
    val participants: MutableMap<UUID, String>
) : Cacheable<UUID>(identifier) {


    fun getGroup(uuid: UUID) = groups.firstOrNull { it.id == (participants[uuid] ?: throw NotFoundException("")) }
    fun canWrite(uuid: UUID) = getGroup(uuid)?.permissions?.contains(ChannelPermission.WRITE) ?: false
    fun canChangeMotd(uuid: UUID) = getGroup(uuid)?.permissions?.contains(ChannelPermission.MOTD) ?: false
    fun canMute(uuid: UUID) = getGroup(uuid)?.permissions?.contains(ChannelPermission.MUTE) ?: false

    fun getPrefix(uuid: UUID) = getGroup(uuid)?.prefix ?: ""


    companion object {
        fun default(owner: UUID?): ChannelData {
            return ChannelData(UUID.randomUUID(), "cool", "c", mutableListOf<Group>().apply {
                add(
                    Group(
                        "owner", "OWNER",
                        mutableSetOf(ChannelPermission.WRITE, ChannelPermission.MUTE)
                    )

                )
                add(
                    Group(
                        "default", "DEFAULT",
                        mutableSetOf(ChannelPermission.WRITE)
                    )
                )
                add(
                    Group(
                        "guest", "GUEST",
                        mutableSetOf()
                    )
                )
            }, "default", owner, mutableMapOf<UUID, String>().apply { this[owner ?: return@apply] = "owner" })
        }
    }
}