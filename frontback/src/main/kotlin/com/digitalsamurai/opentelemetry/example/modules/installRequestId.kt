package com.digitalsamurai.opentelemetry.example.modules

import com.digitalsamurai.opentelemetry.example.ServerHeaders
import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.request.*
import java.util.*

fun Application.installRequestId() {
    // request id setup
    install(CallId) {
        // получаем существующий request-id от клиента (или балансера)
        retrieve { call ->
            call.request.header(ServerHeaders.X_REQUEST_ID.httpName)
        }
        // если не был найден, то создаем собственный request-id
        generate { _ ->
            UUID.randomUUID().toString()
        }
        replyToHeader(ServerHeaders.X_REQUEST_ID.httpName)
    }
}