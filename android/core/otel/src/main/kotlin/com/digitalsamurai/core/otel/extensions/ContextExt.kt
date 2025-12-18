package com.digitalsamurai.core.otel.extensions

import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.core.otel.extensions.models.TracedScope
import io.opentelemetry.context.Context
import io.opentelemetry.extension.kotlin.asContextElement
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// через crossinline запрещаем выпрыгивать из внешней функции, чтобы корректно завершить трейсинг
public suspend inline fun <T> withTracedContext(
    name: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline block: suspend TracedScope.() -> T
): T {
    return withContext(dispatcher + Context.current().asContextElement()) {
        val span = Otel.tracer()
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