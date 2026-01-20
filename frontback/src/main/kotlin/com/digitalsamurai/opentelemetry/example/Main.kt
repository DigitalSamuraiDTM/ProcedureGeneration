package com.digitalsamurai.opentelemetry.example

import com.digitalsamurai.opentelemetry.example.database.DatabaseInteractor
import com.digitalsamurai.opentelemetry.example.modules.installLogging
import com.digitalsamurai.opentelemetry.example.modules.installOtel
import com.digitalsamurai.opentelemetry.example.modules.installRequestId
import com.digitalsamurai.opentelemetry.example.modules.installSerialization
import com.digitalsamurai.opentelemetry.example.opentelemetry.requestSpan
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.delay
// TODO докер пропускает просто напросто стадию сборки и сразу копирование делает

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
    print("INIT: ${environmentData}")
    installRequestId()
    installSerialization()
    installLogging()
    installOtel(otelData = environmentData.otel, disabledTracingRequests = OTEL_EXCLUDE_REQUESTS)

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
        get("/api/v1/diagram/neighbor/configuration") {
            // TODO with other service
        }
    }
}

// todo в идеале сделать RequestRegistrator и регистрировать запросы так, как мы хотим, определяя исключения
private val OTEL_EXCLUDE_REQUESTS = setOf(
    "/favicon.ico"
)