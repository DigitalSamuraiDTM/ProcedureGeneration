package com.digitalsamurai.opentelemetry.example.modules

import com.digitalsamurai.opentelemetry.example.opentelemetry.OtelHolder
import com.digitalsamurai.opentelemetry.example.opentelemetry.buildOpenTelemetryPlugin
import io.ktor.server.application.*

fun Application.installOtel(
    disabledTracingRequests: Set<String>,
) {
    val openTelemetry = OtelHolder.get()
    install(buildOpenTelemetryPlugin(disabledTracingRequests))
}