package com.digitalsamurai.opentelemetry.example.database

import com.digitalsamurai.opentelemetry.example.models.BilinearDiagramConfigurationDto
import com.digitalsamurai.opentelemetry.example.models.NeighborConfigurationDto
import com.digitalsamurai.opentelemetry.example.opentelemetry.childTrace
import com.digitalsamurai.opentelemetry.example.opentelemetry.withTracedContext
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.delay
import kotlin.random.Random

// пример, будто это база данных
class DatabaseInteractor {

    suspend fun getDiagramConfiguration(
        parentSpan: Span,
    ): BilinearDiagramConfigurationDto = parentSpan.childTrace("dbSelectBilinearConfiguration") {

        // представим, что мы идем в бд или в кеш за данными
        delay(100)
        BilinearDiagramConfigurationDto(1000, 1000, 4)
    }

    suspend fun getNeighborConfiguration(): NeighborConfigurationDto = withTracedContext("dbSelectNeighborConfiguration") {

        // представим, что мы идем в бд или в кеш за данными
        delay(50L + Random.nextInt(100))
        NeighborConfigurationDto(1000, 1000, 20)
    }
}