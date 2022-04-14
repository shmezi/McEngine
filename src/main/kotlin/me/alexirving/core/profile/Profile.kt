package me.alexirving.core.profile

import me.alexirving.core.effects.Effect
import java.util.*

data class Profile(val uuid: UUID, val activeEffects: MutableMap<Effect, Int>)