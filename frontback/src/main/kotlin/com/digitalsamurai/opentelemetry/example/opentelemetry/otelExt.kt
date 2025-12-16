package com.digitalsamurai.opentelemetry.example.opentelemetry

import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.ResponseBodyReadyForSend
import io.ktor.server.application.hooks.ResponseSent
import io.ktor.server.request.path
import io.ktor.server.routing.RoutingCall
import io.ktor.util.AttributeKey
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.context.Context
import io.opentelemetry.context.propagation.TextMapGetter

fun buildOpenTelemetryPlugin(disabledRequests: Set<String>) = createApplicationPlugin("opentelemetry") {
    val spanKey = AttributeKey<Span>("otel-span")
    val propagator = OtelHolder.get().propagators.textMapPropagator
    val tracer = OtelHolder.get().getTracer("tracer")
    val textMapGetter = object: TextMapGetter<Headers> {
        override fun keys(carrier: Headers): Iterable<String?> =carrier.names()
        override fun get(carrier: Headers?, key: String): String? = carrier?.get(key)
    }
    onCall { call ->
        if (disabledRequests.contains(call.request.path())) {
            return@onCall
        }
        val parentContext = propagator.extract(Context.current(), call.request.headers, textMapGetter)
        val requestSpan = tracer.spanBuilder(call.request.path()).setParent(parentContext).startSpan()
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
        null -> StatusCode.UNSET
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