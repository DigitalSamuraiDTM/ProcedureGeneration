package com.digitalsamurai.jni_test.data.network

import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpRequest
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class AuthorizationRequest: NetworkHttpRequest<AuthorizationRequest.RequestData, AuthorizationRequest.ResponseData>() {

    override val path: String = "/auth"
    override val method: Method = Method.POST
    override val responseDataType: KType = typeOf<ResponseData>()
    override val requestDataType: KType = typeOf<RequestData>()

    @Serializable
    data class RequestData(
        val login: String,
        val password: String,
    )
    data class ResponseData(
        val jwt: String
    )
}