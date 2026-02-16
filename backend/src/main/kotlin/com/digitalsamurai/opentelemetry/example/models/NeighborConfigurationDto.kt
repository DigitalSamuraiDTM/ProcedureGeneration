package com.digitalsamurai.opentelemetry.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NeighborConfigurationDto(
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("points")
    val points: Int
)