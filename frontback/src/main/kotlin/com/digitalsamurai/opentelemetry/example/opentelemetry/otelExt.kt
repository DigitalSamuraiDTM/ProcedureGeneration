package com.digitalsamurai.opentelemetry.example.opentelemetry

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.context.Context
import io.opentelemetry.context.propagation.TextMapGetter
import io.opentelemetry.extension.kotlin.asContextElement
import io.opentelemetry.sdk.internal.AttributesMap
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun buildOpenTelemetryPlugin(disabledRequests: Set<String>) = createApplicationPlugin("opentelemetry") {
    val spanKey = AttributeKey<Span>("otel-span")
    val propagator = OtelHolder.get().propagators.textMapPropagator
    val tracer = OtelHolder.get().getTracer("tracer")
    val textMapGetter = object : TextMapGetter<Headers> {
        override fun keys(carrier: Headers): Iterable<String?> = carrier.names()
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

// через crossinline запрещаем выпрыгивать из внешней функции, чтобы корректно завершить трейсинг
internal suspend inline fun <T> withTracedContext(
    name: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline block: suspend TracedScope.() -> T
): T {
    return withContext(dispatcher + Context.current().asContextElement()) {
        val span = OtelHolder.get().getTracer("Main")
            .spanBuilder(name)
            .startSpan()
            .apply { makeCurrent() }
        val tracedScope = TracedScope(this, span)
        try {
            val result = tracedScope.block()
            span.endWithSuccess()
            result
        } catch (e: CancellationException) {
            span.addEvent("CoroutineCancellation")
            span.endWithUnknown()
            throw e
        } catch (e: Exception) {
            span.endWithException(e)
            throw e
        }
    }
}


fun Span.addEvent(eventName: String, attributes: Map<String, String>) {
    val attributes = AttributesMap.create(attributes.size.toLong(), attributes.size).apply {
        putAll(attributes.mapKeys { entry -> io.opentelemetry.api.common.AttributeKey.stringKey(entry.key) })
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

fun Span.setException(t: Throwable) {
    setAttribute("exception", t.localizedMessage ?: "no message")
}

/**
 * Выполняем блок кода, пропуская отмену корутины, если происходила на участке.
 * Позволяет не учитывать пользовательскую отмену корутины во время поиска (с дебаунсом) или отмены действия
 */
public suspend inline fun <R> Span.runResultExceptCancellation(block: suspend Span.() -> R): Result<R> {
    return try {
        val calculated = this.block()
        endWithSuccess()
        Result.success(calculated)
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        endWithUnknown()
        throw e
    } catch (e: Throwable) {
        endWithException(e)
        Result.failure(e)
    }
}