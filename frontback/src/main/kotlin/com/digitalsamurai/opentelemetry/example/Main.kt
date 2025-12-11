package com.digitalsamurai.opentelemetry.example
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
    // start server when all systems ready
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

internal fun Application.module() {
    installRequestId()
    installSerialization()
    installLogging()
    installOtel(OTEL_EXCLUDE_REQUESTS)

    val databaseInteractor = DatabaseInteractor()
    routing {
        get("/") {
            call.respondText("Hello world")
        }
        get("/api/v1/memchik") {
            delay(500)
            call.respondText("Сидят как-то 5 голых мужиков в бане...")
        }

        get("/api/v1/diagram/configuration") {
            val number = call.request.queryParameters["number"]?.toInt() ?: throw IllegalArgumentException("illegal argument")
            call.respond(HttpStatusCode.OK, databaseInteractor.getDiagramConfiguration(number, call.requestSpan()))
        }
        post("/api/v1/diagram/save") {

        }
    }
}

// todo в идеале сделать RequestRegistrator и регистрировать запросы так, как мы хотим, определяя исключения
private val OTEL_EXCLUDE_REQUESTS = setOf(
    "/favicon.ico"
)