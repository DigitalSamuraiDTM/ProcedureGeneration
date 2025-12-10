package com.digitsamurai.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Int.pixToDp(): Dp {
    val v = this
    return with(LocalDensity.current) { v.toDp() }
}