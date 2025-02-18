package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import kotlin.random.Random

import java.util.UUID

object MCCvalues {
    val vmcc = mapOf(
        "5411" to "FOOD",
        "5412" to "FOOD",
        "5811" to "MEAL",
        "5812" to "MEAL"
    )
}

object Merchantvalues {
    val merch = mapOf(
        "EATS" to "5812",
        "PADARIA" to "5811",
        "MERCADO" to "5411",
    )
}

@Serializable
data class Account (
    val id: Long = Random.nextLong(1, 10000),
    val accountId: String,
    val amount: Double,
    val mcc: String,
    val merchant : String
)

object Accounts : Table("accounts") {
    val id = long("id").autoIncrement()
    val accountId = varchar("accountId", 50)
    val amount = double("amount")
    val mcc = varchar("mcc", 10)
    val merchant = varchar("merchant", 100)

    override val primaryKey = PrimaryKey(id)
}


object BalanceAccount {
    var FOOD: Double = 800.00
    var MEAL: Double = 500.00
    var CASH: Double = 300.00
}

@Serializable
open class Response(
    val code: String
)
