package com.digitalsamurai.core.otel

import android.app.Application
import android.util.Log
import com.digitsamurai.core.otel.BuildConfig
import io.opentelemetry.android.OpenTelemetryRum
import io.opentelemetry.android.config.OtelRumConfig
import io.opentelemetry.android.features.diskbuffering.DiskBufferingConfig
import io.opentelemetry.android.instrumentation.activity.ActivityLifecycleInstrumentation
import io.opentelemetry.api.common.AttributeKey.stringKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter

class Otel() {

    /**
     * Core element
     */
    private var otel: OpenTelemetryRum? = null

    fun tracer(): Tracer = mainTracer()


    internal fun mainTracer(): Tracer {
        return otel!!.openTelemetry.getTracer(MAIN_TRACER_NAME)
    }

    // OTEL INITIALIZATION LOGIC
    fun initOtel(application: Application): Boolean {
        val diskBufferingConfig = DiskBufferingConfig(
            enabled = false,
            maxCacheSize = 10_000_000,
            debugEnabled = BuildConfig.DEBUG
        )
        val otelConfig = OtelRumConfig()
            .setGlobalAttributes {
                // глобальные аттрибуты устанавливаются во все спаны
                Attributes.of(
                    stringKey(GLOBAL_KEY_APP_VERSION), BuildConfig.PROJECT_VERSION
                )
            }.setDiskBufferingConfig(diskBufferingConfig)
        val otelBuilder = OpenTelemetryRum.builder(application, otelConfig)
            .addSpanExporterCustomizer {
                OtlpHttpSpanExporter.builder().setEndpoint(SPAN_RECORD_ENDPOINT).build()
            }
            .addLogRecordExporterCustomizer {
                OtlpHttpLogRecordExporter.builder()
                    .setEndpoint(LOG_RECORD_ENDPOINT)
                    .build()
            }
            .addInstrumentation(ActivityLifecycleInstrumentation())

        // интересно то, что если указать неправильный endpoint для спанов или логов, то otelBuilder.build() создаст объект, только после этого крашнется
        return try {
            otel = otelBuilder.build()
            Log.d("OBAMA", "SESSION STARTED: ${otel!!.rumSessionId}")
            // TODO: такой способ создания спанов не сказать, что хорошо работает, трекает кривовато
            val rootSpan =
                otel!!.openTelemetry.getTracer(MAIN_TRACER_NAME).spanBuilder("AppStarted")
                    .startSpan()
            Context.current().with(rootSpan).makeCurrent()
            rootSpan.end()
            true
        } catch (e: Throwable) {
            Log.d("OBAMA", "OTEL BUILD FALED: ${e}")
            false
        }
    }
    private companion object {
        const val GLOBAL_KEY_SESSION_ID = "session_id"
        const val GLOBAL_KEY_APP_VERSION = "app_version"

        const val LOG_RECORD_ENDPOINT = BuildConfig.OTEL_HOST + "/v1/logs"
        const val SPAN_RECORD_ENDPOINT = BuildConfig.OTEL_HOST + "/v1/traces"

        const val MAIN_TRACER_NAME = "AppGenerator"
    }
}