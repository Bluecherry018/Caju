package com.example.model

import com.example.configureDatabases
import com.example.configureRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module, host = "0.0.0.0").start(wait = true)
}

fun Application.module() {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
    }

    val repository = FakeAccountRepository()

    configureSerialization(repository)
    configureDatabases()
    configureRouting()

}
