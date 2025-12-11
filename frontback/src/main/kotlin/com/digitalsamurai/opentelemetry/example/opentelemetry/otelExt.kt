package com.digitalsamurai.opentelemetry.example.opentelemetry

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.ResponseSent
import io.ktor.server.request.path
import io.ktor.server.routing.RoutingCall
import io.ktor.util.AttributeKey
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

fun Span.childSpan(name: String): Span {
    return OtelHolder.childSpan(this, name)
}

fun RoutingCall.requestSpan(): Span {
    return this.attributes[AttributeKey<Span>("otel-span")]
}

suspend fun <T> Span.childTrace(spanName: String, make: suspend Span.() -> T): T {
    val child = childSpan(spanName)
    return try {
        child.make().also {
            child.setStatus(StatusCode.OK)
            child.end()
        }
    } catch (e: Exception) {
        child.setStatus(StatusCode.ERROR)
        child.setAttribute("exception", e.localizedMessage)
        child.end()
        throw e
    } finally {
        child.setStatus(StatusCode.UNSET)
        child.end()
    }
}