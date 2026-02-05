package com.digitalsamurai.core.otel.extensions

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.sdk.internal.AttributesMap
import kotlin.coroutines.cancellation.CancellationException

// TODO нужна своя обертка нормальная
fun Span.addEvent(eventName: String, attributes: Map<String, Any>) {
    val attributes = AttributesMap.create(attributes.size.toLong(), attributes.size).apply {
        putAll(
            attributes
            .mapValues { (key, value) -> value.toString() }
            .mapKeys { entry -> AttributeKey.stringKey(entry.key) }
        )
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
    } catch (e: CancellationException) {
        endWithUnknown()
        throw e
    } catch (e: Throwable) {
        endWithException(e)
        Result.failure(e)
    }
}