package com.digitalsamurai.jni_test.core.modifier

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.digitalsamurai.core.otel.extensions.addEvent
import com.digitalsamurai.jni_test.core.composition.LocalScreenSpan

@Composable
fun Modifier.tracedClickable(
    elementName: String,
    onClick: () -> Unit
): Modifier {
    val screenSpan = LocalScreenSpan.current
    return clickable {
        screenSpan.addEvent("clicked", mapOf("element" to elementName))
        onClick()
    }
}
