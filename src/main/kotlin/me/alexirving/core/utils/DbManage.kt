package me.alexirving.core.utils

import me.alexirving.core.db.Database

interface DbManage : Manager {
    val db: Database
}