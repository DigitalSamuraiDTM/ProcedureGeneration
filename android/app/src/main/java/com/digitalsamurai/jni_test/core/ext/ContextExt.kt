package com.digitalsamurai.jni_test.core.ext

import android.content.Context
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.GeneratorApplication
import io.opentelemetry.api.trace.Span

fun Context.startScreenSpan(screenName: String): Span {
    return Otel.tracer().spanBuilder(screenName).startSpan()
}
