package me.alexirving.core.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

abstract class Cacheable : Cloneable {


    @JsonProperty
    var uuid: String


    constructor() {
        this.uuid = UUID.randomUUID().toString()
    }

    constructor(uuid: UUID) {
        this.uuid = uuid.toString()
    }
    @JsonIgnore
    fun getUUID() = UUID.fromString(uuid)

    public override fun clone(): Cacheable {
        return super.clone() as Cacheable
    }
}