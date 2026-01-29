plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    id("kotlinx-serialization")
    application
}

group = "com.digitalsamurai.opentelemetry.example"
version = "1.0.0"
application {
    mainClass.set("com.digitalsamurai.opentelemetry.example.MainKt")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(libs.ktorm.core)
    // LOGGING
    implementation(libs.logback)
    // KTORM
    implementation(libs.ktorm.core)
    implementation(libs.ktorm.driver.postgresql)
    // POSTGRESQL
    implementation("org.postgresql:postgresql:42.7.7")
    // KTOR
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.statuspage)
    implementation(libs.ktor.server.contentnegotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.server.cio)
    testImplementation(libs.ktor.test)
    implementation(libs.ktor.test)
    // OPENTELEMETRY
    implementation(libs.otel.api.incubator)
    implementation(libs.otel.exporter.otlp)
    implementation(libs.otel.sdk)
    implementation(libs.otel.api)
    implementation(libs.otel.semconv)
    implementation(libs.otel.ktor)
    implementation(libs.otel.kotlin.extension)
    // KOTLINX
    implementation(libs.kotlinx.serialization.json)
    // KTOR CLIENT
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.contentnegotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.okhttp)

}