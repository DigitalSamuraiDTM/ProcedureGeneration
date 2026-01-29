package com.digitalsamurai.opentelemetry.example

import com.digitalsamurai.opentelemetry.example.auth.AuthInteractor
import com.digitalsamurai.opentelemetry.example.auth.getAuthorized
import com.digitalsamurai.opentelemetry.example.database.DatabaseInteractor
import com.digitalsamurai.opentelemetry.example.modules.installLogging
import com.digitalsamurai.opentelemetry.example.modules.installOtel
import com.digitalsamurai.opentelemetry.example.modules.installRequestId
import com.digitalsamurai.opentelemetry.example.modules.installSerialization
import com.digitalsamurai.opentelemetry.example.opentelemetry.requestSpan
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay

fun main(args: Array<String>) {
    val environmentData = EnvironmentData.fromArgsOrSystemEnv(args)
    // start server when all systems ready
    embeddedServer(
        factory = Netty,
        port = environmentData.server.port,
        host = environmentData.server.host,
        module = {
            module(environmentData)
        }
    ).start(wait = true)
}

internal fun Application.module(environmentData: EnvironmentData) {
    val auth = AuthInteractor(environmentData.services.auth)

    installRequestId()
    installSerialization()
    installLogging()
    installOtel(otelData = environmentData.services.otel, disabledTracingRequests = OTEL_EXCLUDE_REQUESTS)

    val databaseInteractor = DatabaseInteractor()
    routing {

        get("/") {
            call.respondText("Hello world")
        }
        get("/api/v1/memchik") {
            delay(500)
            call.respondText("Сидят как-то 5 голых мужиков в бане...")
        }

        get("/api/v1/diagram/bilinear/configuration") {
            call.respond(HttpStatusCode.OK, databaseInteractor.getDiagramConfiguration(call.requestSpan()))
        }

        getAuthorized(auth, "/api/v1/diagram/neighbor/configuration") {
            call.respond(HttpStatusCode.OK, databaseInteractor.getNeighborConfiguration())
        }
    }
}

// todo в идеале сделать RequestRegistrator и регистрировать запросы так, как мы хотим, определяя исключения
private val OTEL_EXCLUDE_REQUESTS = setOf(
    "/favicon.ico"
)