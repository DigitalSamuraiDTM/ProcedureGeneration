package com.digitalsamurai.jni_test.core.views

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.digitalsamurai.core.otel.extensions.addEvent
import com.digitalsamurai.jni_test.core.composition.LocalScreenSpan

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    buttonName: String = "Button",
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val screenSpan = LocalScreenSpan.current
    Button(
        modifier = modifier,
        onClick = {
            screenSpan.addEvent("clicked", mapOf("element" to buttonName))
            onClick()
        },
        content = content,
    )
}