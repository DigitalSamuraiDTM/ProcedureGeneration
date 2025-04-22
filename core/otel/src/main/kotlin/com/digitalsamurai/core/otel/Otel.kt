package com.digitalsamurai.core.otel

import android.app.Application
import android.util.Log
import io.opentelemetry.android.BuildConfig
import io.opentelemetry.android.OpenTelemetryRum
import io.opentelemetry.android.agent.setActivityTracerCustomizer
import io.opentelemetry.android.agent.setFragmentTracerCustomizer
import io.opentelemetry.android.config.OtelRumConfig
import io.opentelemetry.android.features.diskbuffering.DiskBufferingConfig
import io.opentelemetry.api.common.AttributeKey.stringKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter


internal class Otel(
    private val application: Application,
    private val sessionId: String,
) {

    private val diskBufferingConfig = DiskBufferingConfig(
        enabled = true,
        maxCacheSize = 10_000_000,
        debugEnabled = true
    )
    private val otelConfig = OtelRumConfig()
        .setGlobalAttributes {
            // глобальные аттрибуты устанавливаются во все спаны
            Attributes.of(stringKey(GLOBAL_KEY_SESSION_ID), sessionId)
            Attributes.of(stringKey(GLOBAL_KEY_APP_VERSION), "1.0.0")
        }.setDiskBufferingConfig(diskBufferingConfig)


    /**
     * Core element
     */
    private var otel: OpenTelemetryRum? = null

    fun initOtel(): Boolean {
        //TODO: поднять нормальный хост
        val otelBuilder = OpenTelemetryRum.builder(application, otelConfig)
            .addSpanExporterCustomizer {
                OtlpHttpSpanExporter.builder().setEndpoint("http://10.0.2.2:4318/v1/traces").build()
            }
            .addLogRecordExporterCustomizer {
                OtlpHttpLogRecordExporter.builder()
                    .setEndpoint("http://10.0.2.2:4318/v1/logs")
                    .build()
            }
        // интересно то, что если указать неправильный endpoint для спанов или логов, то otelBuilder.build() создаст объект, только после этого крашнется
       return try {
           otel = otelBuilder.build()
           Log.d("OBAMA", "SESSION STARTED: ${otel!!.rumSessionId}")
           true
        } catch (e: Throwable) {
            Log.d("OBAMA", "OTEL BUILD FALED: ${e}")
           false
        }
    }

    private companion object {
        const val GLOBAL_KEY_SESSION_ID = "session_id"
        const val GLOBAL_KEY_APP_VERSION = "app_version"
    }
}