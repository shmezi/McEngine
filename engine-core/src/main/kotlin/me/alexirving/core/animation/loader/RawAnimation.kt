package me.alexirving.core.animation.loader

import com.fasterxml.jackson.annotation.JsonProperty

data class RawAnimation(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("delay")
    val delay: Int,
    @JsonProperty("entities")
    val entities: Map<String, String>,
    @JsonProperty("frames")
    val frames: List<List<Map<String, Any>>>,
)