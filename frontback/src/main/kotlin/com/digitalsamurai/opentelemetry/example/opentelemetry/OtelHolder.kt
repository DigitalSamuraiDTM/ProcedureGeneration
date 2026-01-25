package com.digitalsamurai.opentelemetry.example.opentelemetry

import com.digitalsamurai.opentelemetry.example.EnvironmentData
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.Context
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.common.export.MemoryMode
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.semconv.ServiceAttributes


internal object OtelHolder {

    // wast way to create span
    fun newSpan(name: String): Span {
        return get().getTracer("tracer").spanBuilder(name).startSpan()
    }

    fun childSpan(parentSpan: Span, name: String): Span {
        return get().getTracer("tracer")
            .spanBuilder(name)
            .setParent(Context.current().with(parentSpan))
            .startSpan()
    }

    fun get(): OpenTelemetry {
        return GlobalOpenTelemetry.get()
    }

    fun register(otelData: EnvironmentData.Services.Otel) {
        print("OTEL DATA: $otelData")
        val resources = Resource.builder()
            .put(ServiceAttributes.SERVICE_NAME, "Frontback")
            .put(ServiceAttributes.SERVICE_VERSION, "2.2.8")
            .build()
        val spanExporter = OtlpHttpSpanExporter.builder()
            .setMemoryMode(MemoryMode.IMMUTABLE_DATA)
            .setEndpoint("http://${otelData.host}:${otelData.port}/v1/traces")
            .build()
        val logExporter = OtlpHttpLogRecordExporter.builder()
            .setEndpoint("http://${otelData.host}:${otelData.port}/v1/logs")
            .build()

        val sdkTracerProvider = SdkTracerProvider.builder()
            .addResource(resources)
            .addSpanProcessor(SimpleSpanProcessor.builder(spanExporter).build())
            .build()
        val sdkLogProvider = SdkLoggerProvider.builder()
            .addResource(resources)
            .addLogRecordProcessor(SimpleLogRecordProcessor.create(logExporter))
            .build()
        OpenTelemetrySdk.builder()
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .setLoggerProvider(sdkLogProvider)
            .setTracerProvider(sdkTracerProvider)
            .buildAndRegisterGlobal()
    }
}
