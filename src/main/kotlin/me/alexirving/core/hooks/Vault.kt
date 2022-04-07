/*
 * (C) 17/03/2022, 10:00 - Alex Irving | All rights reserved
 * Vault.kt - is part of the McEngine!
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alex Irving <alexirving992@gmail.com>, day month year
 */

package me.alexirving.core.hooks

import me.alexirving.core.economy.EcoManager
import net.milkbowl.vault.economy.AbstractEconomy
import net.milkbowl.vault.economy.EconomyResponse
import java.util.*
import kotlin.math.floor

class Vault(val em: EcoManager) : AbstractEconomy() {
    override fun isEnabled() = true

    override fun getName() = "McEngine"

    override fun hasBankSupport() = false

    override fun fractionalDigits() = 1

    override fun format(amount: Double) = "${floor(amount)}"

    override fun currencyNamePlural() = "EngineEcos"

    override fun currencyNameSingular() = "EngineEco"

    override fun hasAccount(playerName: String) = false

    override fun hasAccount(playerName: String?, worldName: String?) = false

    override fun getBalance(playerName: String): Double = em.getEco("default")?.getBal(UUID.fromString(playerName))?:0.0

    override fun getBalance(playerName: String?, world: String?): Double {
        TODO("Not yet implemented")
    }

    override fun has(playerName: String?, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun createBank(name: String?, player: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun deleteBank(name: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankBalance(name: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun getBanks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(playerName: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        TODO("Not yet implemented")
    }
}