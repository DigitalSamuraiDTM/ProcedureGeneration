package com.digitalsamurai.opentelemetry.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiagramConfigurationDto(

    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("colors")
    val colorsList: List<Long>,
    @SerialName("count_points")
    val countPoints: Int,
)