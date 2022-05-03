package me.alexirving.core.utils.manager

import me.alexirving.core.db.Database

interface DbManage : Manager {
    val db: Database
}