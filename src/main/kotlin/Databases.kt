package com.example

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

fun Application.configureDatabases() {
    val url = System.getenv("DB_URL") ?: "jdbc:mysql://localhost:3306/account"
    val driver = "com.mysql.cj.jdbc.Driver"
    val user = System.getenv("DB_USER") ?: "root"
    val password = System.getenv("DB_PASSWORD") ?: ""

    Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )
}