package com.digitalsamurai.jni_test.data.network.requests.auth

import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpRequest

abstract class AuthNetworkHttpRequest<REQUEST_DATA : Any, RESPONSE_DATA : Any> : NetworkHttpRequest<REQUEST_DATA, RESPONSE_DATA>() {
    override val host: String = "10.0.2.2"
    override val port: Int = 8081
}