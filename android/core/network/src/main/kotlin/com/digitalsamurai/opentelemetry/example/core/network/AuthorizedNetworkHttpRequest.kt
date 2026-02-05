package com.digitalsamurai.opentelemetry.example.core.network

import com.digitalsamurai.opentelemetry.example.core.network.models.Jwt

abstract class AuthorizedNetworkHttpRequest<REQUEST_DATA : Any, RESPONSE_DATA : Any>(
    open val jwt: Jwt,
) : NetworkHttpRequest<REQUEST_DATA, RESPONSE_DATA>()