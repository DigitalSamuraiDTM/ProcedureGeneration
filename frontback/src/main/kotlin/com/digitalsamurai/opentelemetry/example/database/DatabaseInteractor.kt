package com.digitalsamurai.opentelemetry.example.database

import com.digitalsamurai.opentelemetry.example.models.DiagramConfigurationDto
import com.digitalsamurai.opentelemetry.example.opentelemetry.childSpan
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.delay

// пример, будто это база данных
class DatabaseInteractor {

    private val configurationMono = DiagramConfigurationDto(
        width = 700,
        height = 700,
        colorsList = listOf(0xff000000, 0xffffffff, 0xff969696),
        countPoints = 15
    )
    private val configurationRgb = DiagramConfigurationDto(
        width = 700,
        height = 700,
        colorsList = listOf(0xffff0000, 0xff00ff00, 0xff0000ff),
        countPoints = 15
    )

    private val configurationRectangle = DiagramConfigurationDto(
        width = 200,
        height = 700,
        colorsList = listOf(0xffff0000, 0xff00ff00, 0xff0000ff, 0xffff0ae2, 0xff03d9ff, 0xffffe603),
        countPoints = 10
    )

    private val configurationRectangle2 = DiagramConfigurationDto(
        width = 700,
        height = 500,
        colorsList = listOf(0xffff0000, 0xff00ff00, 0xff0000ff, 0xffff0ae2, 0xff03d9ff, 0xffffe603),
        countPoints = 10
    )

    suspend fun getDiagramConfiguration(number: Int, parentSpan: Span): DiagramConfigurationDto {
        val childSpan = parentSpan.childSpan("db-select-diagram-configuration")

        // представим, что мы идем в бд или в кеш за данными
        delay(100)
        return when (number) {
            1 -> configurationMono
            2 -> configurationRgb
            3 -> configurationRectangle
            4 -> configurationRectangle2
            else -> configurationMono
        }.also {
            childSpan.end()
        }
    }
}