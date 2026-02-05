package com.digitalsamurai.jni_test.data.network.requests.frontback

import com.digitalsamurai.opentelemetry.example.core.network.models.Jwt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class GetNeighborConfigRequest(
    override val jwt: Jwt
) : FrontbackAuthorizedNetworkHttpRequest<Unit, GetNeighborConfigRequest.ResponseDto>(jwt) {

    override val path: String = "/api/v1/diagram/neighbor/configuration"
    override val method: Method = Method.GET
    override val responseDataType: KType = typeOf<ResponseDto>()
    override val requestDataType: KType = typeOf<Unit>()

    @Serializable
    data class ResponseDto(
        @SerialName("width")
        val width: Int,
        @SerialName("height")
        val height: Int,
        @SerialName("points")
        val points: Int
    )
}