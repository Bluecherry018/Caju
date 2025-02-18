package com.example.model

import java.util.*

interface AccountRepository{
    fun getMcc(merchant: String): String?
    fun authorize(accountId: String, amount: Double, mcc: String, merchant: String): Response?
    fun getById(transactionId: Long): Account?
}