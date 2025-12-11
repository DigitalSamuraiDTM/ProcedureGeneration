package com.digitalsamurai.opentelemetry.example.core.network

import kotlin.reflect.KType

public abstract class NetworkHttpRequest<REQUEST_DATA : Any, RESPONSE_DATA : Any> {

    public abstract val path: String

    public abstract val method: Method

    public abstract val responseDataType: KType

    public abstract val requestDataType: KType


    public enum class Method {
        POST,
        GET,
        PUT,
        DELETE
    }
}