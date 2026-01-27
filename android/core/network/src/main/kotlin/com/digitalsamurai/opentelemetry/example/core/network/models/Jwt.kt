package com.digitalsamurai.opentelemetry.example.core.network.models

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Jwt(val value: String)