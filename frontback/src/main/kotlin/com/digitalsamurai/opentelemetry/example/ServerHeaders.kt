package com.digitalsamurai.opentelemetry.example

public enum class ServerHeaders(
    val httpName: String
) {
    X_REQUEST_ID("X-Request-Id")
}