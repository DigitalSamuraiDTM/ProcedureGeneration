package com.digitalsamurai.opentelemetry.example.database

import com.digitalsamurai.opentelemetry.example.models.BilinearDiagramConfigurationDto
import com.digitalsamurai.opentelemetry.example.opentelemetry.childTrace
import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.delay

// пример, будто это база данных
class DatabaseInteractor {

    suspend fun getDiagramConfiguration(
        parentSpan: Span,
    ): BilinearDiagramConfigurationDto = parentSpan.childTrace("db-select-diagram-configuration") {

        // представим, что мы идем в бд или в кеш за данными
        delay(100)
        BilinearDiagramConfigurationDto(1000, 1000, 4)
    }

    suspend fun getNeighborConfiguration() {
        TODO()
    }
}