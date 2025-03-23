package com.digitalsamurai.math.data

data class D2Point(
    val x: Float,
    val y: Float,
) {
    fun fX(): Float = y
}

data class D3Point(
    val x: Float,
    val y: Float,
    val z: Float,
) {
    fun fXY(): Float = z

    fun toD2x0y(): D2Point = D2Point(x, y)
}
