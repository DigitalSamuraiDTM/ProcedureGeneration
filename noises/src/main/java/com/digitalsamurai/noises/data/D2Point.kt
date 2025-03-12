package com.digitalsamurai.noises.data

public data class D2Point (
    public val x: Float,
    public val y: Float,
) {
    public fun fX(): Float = y
}

public data class D3Point (
    public val x: Float,
    public val y: Float,
    public val z: Float,
) {
    public fun fXY(): Float = z
}
