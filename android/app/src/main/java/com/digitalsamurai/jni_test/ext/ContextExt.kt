package com.digitalsamurai.jni_test.ext

import android.content.Context
import com.digitalsamurai.jni_test.GeneratorApplication
import io.opentelemetry.api.trace.Span

fun Context.startScreenSpan(screenName: String): Span {
    return (this.applicationContext as GeneratorApplication).otel.tracer().spanBuilder(screenName).startSpan()
}
