/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * EcoManager.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.econemy

import me.alexirving.core.sql.Column
import me.alexirving.core.sql.SqLite.buildTable
import me.alexirving.core.sql.SqLite.deleteTable
import me.alexirving.core.sql.SqLite.getTableNames
import me.alexirving.core.sql.SqLite.psQuery
import java.util.*

class EcoManager {
    private val ecos = mutableMapOf<String, Economy>()

    fun create(id: String): Economy {
        ecos[id] = Economy(id)
        buildTable(id, "uuid", Column("balance", Int, 0))
        return ecos[id] ?: throw NullPointerException("Some weird ass thing happened in EcoManager!")
    }

    fun delete(id: String) {
        ecos.remove(id)
        deleteTable(id)
    }

    fun getEco(id: String) = ecos[id]

    fun reloadEco() {
        ecos.clear()
        for (name in getTableNames()) {
            val e = Economy(name)
            psQuery("SELECT * FROM `$name`;") {
                while (it.next())
                    e.setBal(UUID.fromString(it.getString("uuid")), it.getInt("balance"))
            }
            ecos[name] = e
        }
    }
}