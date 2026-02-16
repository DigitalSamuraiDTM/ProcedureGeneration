package com.digitalsamurai.opentelemetry.example.modules

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.installSerialization() {
    install(ContentNegotiation) {
        json()
    }
}