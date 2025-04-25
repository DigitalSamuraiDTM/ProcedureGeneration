package com.digitalsamurai.core.otel.extensions

import android.content.Context
import com.digitalsamurai.core.otel.OtelApplication
import io.opentelemetry.api.trace.Span


/**
 * TODO()
 */
fun Context.startRootSpan(name: String): Span {
    return (this.applicationContext as OtelApplication).startRootSpan(name)
}

/**
 * TODO()
 */
fun Context.startSpan(name: String): Span {
    return (this.applicationContext as OtelApplication).startSpan(name)
}

/**
 * Создаем span на основе родительского спана
 * @param root это контекст родительского span
 */
fun Context.startSpan(name: String, root: Span): Span {
    return (this.applicationContext as OtelApplication).startSpan(name, root)
}