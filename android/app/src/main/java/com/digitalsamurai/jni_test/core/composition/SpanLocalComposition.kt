package com.digitalsamurai.jni_test.core.composition

import androidx.compose.runtime.compositionLocalOf
import io.opentelemetry.api.trace.Span

val LocalScreenSpan = compositionLocalOf<Span> {
    error("Screen span not provided")
}