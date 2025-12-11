package com.digitalsamurai.opentelemetry.example.modules

import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.*
import org.slf4j.event.Level

internal fun Application.installLogging() {
    install(CallLogging) {
        logger = null
        level = Level.INFO
        mdc("request_id") { call ->
            call.callId
        }
    }
}