package com.digitalsamurai.otel.api

import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.TracerProvider

object Tracer : TracerProvider {
    override fun get(instrumentationScopeName: String): Tracer {

    }
}