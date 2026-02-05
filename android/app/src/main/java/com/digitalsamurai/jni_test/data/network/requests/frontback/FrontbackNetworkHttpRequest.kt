package com.digitalsamurai.jni_test.data.network.requests.frontback

import com.digitalsamurai.opentelemetry.example.core.network.AuthorizedNetworkHttpRequest
import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpRequest
import com.digitalsamurai.opentelemetry.example.core.network.models.Jwt

abstract class FrontbackNetworkHttpRequest<REQUEST_DATA : Any, RESPONSE_DATA : Any> : NetworkHttpRequest<REQUEST_DATA, RESPONSE_DATA>() {
    override val host: String = "10.0.2.2"
    override val port: Int = 8080
}

abstract class FrontbackAuthorizedNetworkHttpRequest<REQUEST_DATA : Any, RESPONSE_DATA : Any>(
    override val jwt: Jwt
) : AuthorizedNetworkHttpRequest<REQUEST_DATA, RESPONSE_DATA>(
    jwt = jwt
) {
    override val host: String = "10.0.2.2"
    override val port: Int = 8080
}