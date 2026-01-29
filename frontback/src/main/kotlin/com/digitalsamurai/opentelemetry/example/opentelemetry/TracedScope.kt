package com.digitalsamurai.opentelemetry.example.opentelemetry

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.context.Context
import io.opentelemetry.context.Scope
import kotlinx.coroutines.CoroutineScope
import java.time.Instant
import java.util.concurrent.TimeUnit

// kotlin не может делегировать с интерфейсами джавы
// https://www.jetbrains.com/help/inspectopedia/JavaDefaultMethodsNotOverriddenByDelegation.html#locating-this-inspection
internal data class TracedScope(
    val coroutineScope: CoroutineScope,
    val span: Span
) : CoroutineScope by coroutineScope, Span by span {
    override fun setAttribute(key: String, value: String?): Span? {
        return span.setAttribute(key, value)
    }

    override fun setAttribute(key: String, value: Long): Span? {
        return span.setAttribute(key, value)
    }

    override fun setAttribute(key: String, value: Double): Span? {
        return span.setAttribute(key, value)
    }

    override fun setAttribute(key: String, value: Boolean): Span? {
        return span.setAttribute(key, value)
    }

    override fun setAttribute(key: AttributeKey<Long?>, value: Int): Span? {
        return span.setAttribute(key, value)
    }

    override fun setAllAttributes(attributes: Attributes): Span? {
        return span.setAllAttributes(attributes)
    }

    override fun addEvent(name: String): Span? {
        return span.addEvent(name)
    }

    override fun addEvent(name: String, timestamp: Long, unit: TimeUnit): Span? {
        return span.addEvent(name, timestamp, unit)
    }

    override fun addEvent(name: String, timestamp: Instant): Span? {
        return span.addEvent(name, timestamp)
    }

    override fun addEvent(
        name: String,
        attributes: Attributes,
        timestamp: Instant
    ): Span? {
        return span.addEvent(name, attributes, timestamp)
    }

    override fun setStatus(statusCode: StatusCode): Span? {
        return span.setStatus(statusCode)
    }

    override fun recordException(exception: Throwable): Span? {
        return span.recordException(exception)
    }

    override fun addLink(spanContext: SpanContext): Span? {
        return span.addLink(spanContext)
    }

    override fun addLink(
        spanContext: SpanContext,
        attributes: Attributes
    ): Span? {
        return span.addLink(spanContext, attributes)
    }

    override fun end(timestamp: Instant) {
        span.end(timestamp)
    }

    override fun storeInContext(context: Context): Context? {
        return span.storeInContext(context)
    }

    override fun makeCurrent(): Scope? {
        return span.makeCurrent()
    }


}