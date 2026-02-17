package com.digitalsamurai.jni_test.core.views

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digitalsamurai.core.otel.extensions.addEvent
import com.digitalsamurai.jni_test.core.composition.LocalScreenSpan

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    buttonName: String = "Button",
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val screenSpan = LocalScreenSpan.current
    Button(
        modifier = modifier,
        onClick = {
            if (!isLoading) {
                screenSpan.addEvent("clicked", mapOf("element" to buttonName))
                onClick()
            }
        },
        content = {
            if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
                    trackColor = MaterialTheme.colorScheme.background
                )
            } else {
                content()
            }
        },
    )
}