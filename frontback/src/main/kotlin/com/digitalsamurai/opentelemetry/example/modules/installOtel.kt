package com.digitalsamurai.opentelemetry.example.modules

import com.digitalsamurai.opentelemetry.example.EnvironmentData
import com.digitalsamurai.opentelemetry.example.opentelemetry.OtelHolder
import com.digitalsamurai.opentelemetry.example.opentelemetry.buildOpenTelemetryPlugin
import io.ktor.server.application.*

fun Application.installOtel(
    otelData: EnvironmentData.OtelData,
    disabledTracingRequests: Set<String>,
) {
    val openTelemetry = OtelHolder.register(otelData)
    install(buildOpenTelemetryPlugin(disabledTracingRequests))
}