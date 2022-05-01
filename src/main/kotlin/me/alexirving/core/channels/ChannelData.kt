package me.alexirving.core.channels

import com.fasterxml.jackson.annotation.JsonIgnore
import me.alexirving.core.exceptions.NotFoundException
import java.util.*

/**
 * Represents data stored about a channel
 * @param internalId the id used for mongoDb (Same as uuid in string form for kmongo.
 * @param
 */
data class ChannelData(
    val uuid: String,
    var name: String,
    var tag: String,
    val groups: MutableList<Group>,
    var default: String,
    var owner: UUID,
    val participants: MutableMap<UUID, String>
) {


    fun getGroup(uuid: UUID) = groups.firstOrNull { it.id == (participants[uuid] ?: throw NotFoundException("")) }
    fun canWrite(uuid: UUID) = getGroup(uuid)?.permissions?.contains(ChannelPermission.WRITE) ?: false
    fun canChangeMotd(uuid: UUID) = getGroup(uuid)?.permissions?.contains(ChannelPermission.MOTD) ?: false
    fun canMute(uuid: UUID) = getGroup(uuid)?.permissions?.contains(ChannelPermission.MUTE) ?: false

    fun getPrefix(uuid: UUID) = getGroup(uuid)?.prefix ?: ""

    @JsonIgnore
    fun getId() = UUID.fromString(uuid)

    companion object {
        fun default(owner: UUID): ChannelData {
            return ChannelData(UUID.randomUUID().toString(), "chat", "", mutableListOf<Group>().apply {
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
            }, "default", owner, mutableMapOf<UUID, String>().apply { this[owner] = "owner" })
        }
    }
}