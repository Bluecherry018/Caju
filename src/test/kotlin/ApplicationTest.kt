package com.example.model
import com.example.configureDatabases
import com.example.configureRouting
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.h2.api.ErrorCode
import java.util.*
import kotlin.test.*
import kotlin.test.assertEquals
import kotlin.math.log
import io.ktor.client.statement.*
import io.mockk.every
import io.mockk.mockk


class ApplicationTest {

    // Teste para de acordo com mcc
    @Test
    fun testAuthorizeOK() = testApplication {
        application { configureDatabases() }

        val repository = mockk<FakeAccountRepository>()

        application {
            configureSerialization(repository)
            configureRouting()

        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val account1 = Account(accountId = "1", amount = 400.00, mcc = "5811", merchant = "PADARIA SANTO ANTONIO     SAO PAULO BR")
        val response1 = client.post("/authorize") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(account1)
        }
        assertEquals(HttpStatusCode.OK, response1.status)
        assertEquals("""{"code":"00"}""", response1.bodyAsText())

    }

    // Teste do fallOut
    @Test
    fun testAuthorizeFallOut() = testApplication {
        application { configureDatabases() }
        val repository = mockk<FakeAccountRepository>()
        application {
            configureSerialization(repository)
            configureRouting()

        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val account2 = Account(accountId = "2", amount = 250.00, mcc = "5411", merchant = "SEUJE SAO PAULO BR")
        val response2 = client.post("/authorize") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(account2)
        }
        assertEquals(HttpStatusCode.OK, response2.status)
        assertEquals("""{"code":"00"}""", response2.bodyAsText())

    }

    // Teste para verificar o mcc de acordo com o merchant
    @Test
    fun testAuthorizeError() = testApplication {
        application { configureDatabases() }
        val repository = mockk<FakeAccountRepository>()
        application {
            configureSerialization(repository)
            configureRouting()

        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val account3 = Account(accountId = "3", amount = 501.00, mcc = "5411", merchant = "UBER EATS SAO PAULO BR")
        val response3 = client.post("/authorize") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(account3)
        }
        assertEquals(HttpStatusCode.OK, response3.status)
        assertEquals("""{"code":"51"}""", response3.bodyAsText())

    }
//
}
