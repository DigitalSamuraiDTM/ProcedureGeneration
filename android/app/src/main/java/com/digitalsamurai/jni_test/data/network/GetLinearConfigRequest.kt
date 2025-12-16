package com.digitalsamurai.jni_test.data.network

import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class GetLinearConfigRequest: NetworkHttpRequest<Unit, GetLinearConfigRequest.ResponseDto>() {

    override val path: String = "/api/v1/diagram/bilinear/configuration"
    override val method: Method = Method.GET
    override val responseDataType: KType = typeOf<ResponseDto>()
    override val requestDataType: KType = typeOf<Unit>()

    @Serializable
    data class ResponseDto(
        @SerialName("width")
        val width: Int,
        @SerialName("height")
        val height: Int,
        @SerialName("resolution")
        val resolution: Int,
    )
}