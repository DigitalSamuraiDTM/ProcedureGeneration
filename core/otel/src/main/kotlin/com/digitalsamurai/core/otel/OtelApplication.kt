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
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter

//TODO трекает неидеально, есть задержки. Надо разобраться откуда они берутся (compose криво лайфсайкл предоставляет или трейсинг не успевает)
abstract class OtelApplication : Application() {

    /**
     * Core element
     */
    private var otel: OpenTelemetryRum? = null


    override fun onCreate() {
        if (isProcessForOtel()) {
            if (initOtel() != true) {
                Log.e("OBAMA", "FAIL INIT OTEL")
            }
        }
        super.onCreate()
    }

    fun startScreenSpan(screenName: String): Span {
        return mainTracer().spanBuilder(screenName).setParent(Context.current()).startSpan()
    }

    /**
     * Создаем span. Этот спан будет привязан к тому спану, чей скоуп сейчас активен
     * То есть: если кто-то вызовет [Span.makeCurrent], то текущий созданный спан автоматически привяжется к нему
     */
    fun startSpan(name: String): Span {
        return mainTracer().spanBuilder(name).startSpan()
    }

    /**
     * Создаем спан на основе родительского спана.
     * Такой подход не особо рекомендуется, так как span сложно передать между большим количеством модулей приложения.
     * Советуется использовать [Context]
     */
    fun startSpan(name: String, root: Span): Span {
        return mainTracer().spanBuilder(name).setParent(Context.current().with(root)).startSpan()
    }

    /**
     * launch otel only in that process, which we need
     */
    abstract fun isProcessForOtel(): Boolean

    // OTEL INITIALIZATION LOGIC
    fun initOtel(): Boolean {
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
        val otelBuilder = OpenTelemetryRum.builder(this, otelConfig)
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

    internal fun mainTracer(): Tracer {
        return otel!!.openTelemetry.getTracer(MAIN_TRACER_NAME)
    }

    override fun onTrimMemory(level: Int) {
        Log.d("OBAMA", "TRIMMED: ${level}")
        super.onTrimMemory(level)
    }

    private companion object {
        const val GLOBAL_KEY_SESSION_ID = "session_id"
        const val GLOBAL_KEY_APP_VERSION = "app_version"

        const val LOG_RECORD_ENDPOINT = BuildConfig.OTEL_HOST + "/v1/logs"
        const val SPAN_RECORD_ENDPOINT = BuildConfig.OTEL_HOST + "/v1/traces"

        const val MAIN_TRACER_NAME = "AppGenerator"
    }
}