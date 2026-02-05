package com.digitalsamurai.opentelemetry.example.auth

import com.digitalsamurai.opentelemetry.example.EnvironmentData
import com.digitalsamurai.opentelemetry.example.opentelemetry.withTracedContext
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*

class AuthInteractor(
    private val data: EnvironmentData.Services.Auth
) {

    val authorizationClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 1000
            connectTimeoutMillis = 1000
            socketTimeoutMillis = 1000
        }
    }

    suspend fun validate(context: RoutingCall): HttpStatusCode = withTracedContext("checkAuth") {
        val authHeader = context.request.headers["Authorization"]
        if (authHeader == null || authHeader == "") {
            return@withTracedContext HttpStatusCode.Unauthorized
        }
        val httpResponse = authorizationClient.request {
            method = HttpMethod.Get
            headers.append("Authorization", authHeader)
            headers.append("traceparent", "00-${spanContext.traceId}-${spanContext.spanId}-01")
            url {
                host = data.host
                port = data.port
                path("/check")
            }
        }
        return@withTracedContext httpResponse.status
    }
}