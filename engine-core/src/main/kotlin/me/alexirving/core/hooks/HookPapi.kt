/*
 * (C) 16/03/2022, 23:33 - Alex Irving | All rights reserved
 * Papi.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.hooks

import me.alexirving.core.McEngine
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

class HookPapi(mcEngine: McEngine) : PlaceholderExpansion() {
    override fun getIdentifier() = "McEngine"

    override fun getAuthor() = "Alex Irving"

    override fun getVersion() = "1.0"
    val econemys = mcEngine
//    override fun onRequest(player: OfflinePlayer, params: String): String {
//        when (params) {
//            "balance-$" -> {}
//            else -> return "404-placeholder_not_found"
//        }
//    }
}