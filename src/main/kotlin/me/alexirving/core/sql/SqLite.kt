/*
 * (C) 15/03/2022, 0:33 - Alex Irving | All rights reserved
 * SqLite.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */
package me.alexirving.core.sql

import me.alexirving.core.utils.asyncNonBukkit
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

object SqLite {
    fun getDb(): Connection = DriverManager.getConnection("jdbc:sqlite:database.db")

    fun psExec(statement: String) = asyncNonBukkit { getDb().prepareStatement(statement).execute() }
    fun psQuery(statement: String, done: (rs: ResultSet) -> Unit) =
        asyncNonBukkit { ; done(getDb().prepareStatement(statement).executeQuery()) }


    fun buildTable(name: String, primary: Any, vararg columns: Column) {
        var s = ""
        for (c in columns) {
            val b = if (c.default != null)
                " DEFAULT `${c.default}`"
            else ""
            s = "$s, ${c.name} ${c.dataType.toSQLType()}(32)$b"

        }

        psExec("CREATE TABLE IF NOT EXISTS `$name` (`$primary` VARCHAR(64), PRIMARY NOT NULL `$primary`);")
    }

    fun deleteTable(name: String) {
        psExec("DROP TABLE `$name`;")
    }

    fun Any.toSQLType(): String = when (this) {
        is Int -> "INT"
        is String -> "VARCHAR"
        else -> "VARCHAR"
    }

    fun getTableNames(): MutableList<String> {
        val ms = mutableListOf<String>()
        psQuery("SELECT name FROM sqlite_schema WHERE type='table';") {
            while (it.next())
                ms.add(it.getString("name"))
        }
        return ms
    }

}