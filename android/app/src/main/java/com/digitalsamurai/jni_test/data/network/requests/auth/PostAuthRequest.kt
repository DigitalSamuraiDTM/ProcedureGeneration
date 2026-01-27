package com.digitalsamurai.jni_test.data.network.requests.auth

import com.digitalsamurai.opentelemetry.example.core.network.models.Jwt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class PostAuthRequest : AuthNetworkHttpRequest<PostAuthRequest.RequestData, PostAuthRequest.ResponseData>() {

    override val path: String = "/auth"
    override val method: Method = Method.POST
    override val responseDataType: KType = typeOf<ResponseData>()
    override val requestDataType: KType = typeOf<RequestData>()

    @Serializable
    data class RequestData(
        @SerialName("login")
        val login: String,
        @SerialName("password")
        val password: String,
    )

    @Serializable
    data class ResponseData(
        @SerialName("token")
        val token: String
    )
}