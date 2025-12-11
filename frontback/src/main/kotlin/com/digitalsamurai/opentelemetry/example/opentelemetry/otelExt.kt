package com.digitalsamurai.opentelemetry.example.opentelemetry

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode

fun buildOpenTelemetryPlugin(disabledRequests: Set<String>) = createApplicationPlugin("opentelemetry") {
    val spanKey = AttributeKey<Span>("otel-span")
    onCall { call ->
        if (disabledRequests.contains(call.request.path())) {
            return@onCall
        }
        val requestSpan = OtelHolder.newSpan(call.request.path())
        call.attributes.put(spanKey, requestSpan)
    }
    on(ResponseSent) { call ->
        if (disabledRequests.contains(call.request.path())) {
            return@on
        }
        val requestSpan = call.attributes[spanKey]
        val spanStatus = call.response.status().toSpanStatus()
        requestSpan.setStatus(spanStatus)
        requestSpan.end()
    }
}

private fun HttpStatusCode?.toSpanStatus(): StatusCode {
    val value = this?.value
    return when (value) {
        null -> StatusCode.ERROR
        200 -> StatusCode.OK
        else -> StatusCode.ERROR
    }
}

public fun Span.childSpan(name: String): Span {
    return OtelHolder.childSpan(this, name)
}

public fun RoutingCall.requestSpan(): Span {
    return this.attributes[AttributeKey<Span>("otel-span")]
}