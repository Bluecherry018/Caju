package com.example.model

import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class FakeAccountRepository: AccountRepository{

    // Função para priorizar o nome do comerciante em vez do mcc(Questão L3)
    override fun getMcc(merchant: String): String?{
        val word = merchant.split('*', ' ')

        val firstword = word.firstOrNull()?.let {Merchantvalues.merch[it]}
        val secondword = word[1].let {Merchantvalues.merch[it]}

        return firstword ?: secondword

    }
    // Função para autorizar a transação (Questão L1)
    override fun authorize(accountId: String, amount: Double, mcc: String, merchant: String): Response? {

        //salvando no banco
        return transaction {
            Accounts.insert {
                it[this.accountId] = accountId
                it[this.amount] = amount
                it[this.mcc] = mcc
                it[this.merchant] = merchant
            }

            val merchantMcc = getMcc(merchant) ?: mcc

            val category = MCCvalues.vmcc[merchantMcc] ?: "CASH"

            val balance = when (category) {
                "FOOD" -> BalanceAccount.FOOD
                "MEAL" -> BalanceAccount.MEAL
                else -> BalanceAccount.CASH
            }

            if (balance >= amount) {
                when (category) {
                    "FOOD" -> BalanceAccount.FOOD -= amount
                    "MEAL" -> BalanceAccount.MEAL -= amount
                    else -> BalanceAccount.CASH -= amount
                }
                return@transaction Response("00")
            //Fallback (Questão L2)
            } else if (balance < amount && BalanceAccount.CASH >= amount) {
                BalanceAccount.CASH -= amount
                return@transaction Response("00")
            } else if (balance < amount) {
                return@transaction Response("51")
            }
            return@transaction Response("07")
        }
    }

    // Caso queira pesquisar uma transação pelo id
    override fun getById(transactionId: Long): Account? {
        return transaction {
            Accounts.selectAll().where { Accounts.id eq  transactionId}
                .map {
                    Account(
                        id = it[Accounts.id],
                        accountId = it[Accounts.accountId],
                        amount = it[Accounts.amount],
                        mcc = it[Accounts.mcc],
                        merchant = it[Accounts.merchant]
                    )
                }
                .singleOrNull()
        }
    }

}