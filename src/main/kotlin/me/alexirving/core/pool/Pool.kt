package me.alexirving.core.pool

class Pool<T> {
    //TODO: Finish at some point :)
    var max: Int
    private val available = mutableListOf<T>()
    private val inuse = mutableListOf<T>()

    constructor(max: Int) {
        this.max = max
    }

    constructor() {
        this.max = -1
    }

}