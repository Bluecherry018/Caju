package com.example.model

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*
import java.util.*
import kotlin.random.Random

fun Application.configureSerialization(repository: AccountRepository) {
    install(ContentNegotiation) {
        json()
    }


    routing {

        // Pega a transação pelo id
        get("/transaction/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@get
            }

            val transaction = repository.getById(id)

            if (transaction != null) {
                call.respond(transaction)
            } else {
                call.respond(HttpStatusCode.NotFound, "Transaction not found")
            }
        }

        // pega o saldo da carteira
        get("/balance") {
            val balances = mapOf(
                "MEAL" to BalanceAccount.MEAL,
                "FOOD" to BalanceAccount.FOOD,
                "CASH" to BalanceAccount.CASH
            )
            call.respond(balances)
        }
        // autorizador da transação
        post("/authorize"){
                val account = call.receive<Account>()
                val response = repository.authorize(account.accountId, account.amount,account.mcc,account.merchant)
                if (response != null) {
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Authorization failed")
                }
        }
    }
}
