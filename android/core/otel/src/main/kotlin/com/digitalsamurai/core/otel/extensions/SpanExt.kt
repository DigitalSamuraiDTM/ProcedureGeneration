package com.digitalsamurai.core.otel.extensions

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.sdk.internal.AttributesMap

// TODO нужна своя обертка нормальная
fun Span.addEvent(eventName: String, attributes: Map<String, String>) {
    val attributes = AttributesMap.create(attributes.size.toLong(), attributes.size).apply {
        putAll(attributes.mapKeys { entry -> AttributeKey.stringKey(entry.key) })
    }
    addEvent(eventName, attributes)
}

fun Span.endWithException(e: Throwable?) {
    e?.let { setAttribute("exception", e.toString()) }
    setStatus(StatusCode.ERROR)
    end()
}

fun Span.endWithSuccess() {
    setStatus(StatusCode.OK)
    end()
}

fun Span.endWithUnknown() {
    setStatus(StatusCode.UNSET)
    end()
}