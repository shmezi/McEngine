package me.alexirving.core.channels

data class Group(val id: String, var prefix: String, val permissions: MutableSet<ChannelPermission>)