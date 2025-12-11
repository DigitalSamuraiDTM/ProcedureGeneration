package com.digitalsamurai.opentelemetry.example.core.network

import com.digitalsamurai.opentelemetry.example.core.network.polling.PollingOptions
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.typeInfo
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

// TODO вообще запросы надо делать умнее. Надо чтобы мы умели на каждый HTTP response code обрабатывать разные кейсы и, возможно, делать, разную десериализацию
public class NetworkHttpClient(
    public val hostAddress: String,
    public val portAddress: Int
) {

    public val client: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 30_000
        }
    }

    public suspend inline fun <reified REQUEST_DATA : Any, reified RESPONSE_DATA : Any> makeNetworkRequestWithServerResult(
        networkHttpRequest: NetworkHttpRequest<REQUEST_DATA, RESPONSE_DATA>,
        data: REQUEST_DATA,
    ): Result<RESPONSE_DATA> = runCatchingExceptCancellation {
        val httpResponse = client.request {
            method = networkHttpRequest.method.toKtorMethod()
            url {
                host = hostAddress
                port = portAddress
                path(networkHttpRequest.path)
            }
            if (method == HttpMethod.Get) {
                Json.encodeToJsonElement(data).jsonObject.entries.forEach { jsonEntry ->
                    url.parameters.append(jsonEntry.key, jsonEntry.value.toString().trim('"'))
                }
            } else {
                contentType(ContentType.Application.Json)
                setBody(data, typeInfo<REQUEST_DATA>())
            }
        }
        val responseBody = httpResponse.body<RESPONSE_DATA>()
        responseBody
    }

    public inline fun <reified REQUEST_DATA : Any, reified RESPONSE_DATA : Any> pollNetworkRequestWithServerResult(
        networkHttpRequest: NetworkHttpRequest<REQUEST_DATA, RESPONSE_DATA>,
        data: REQUEST_DATA,
        pollingOptions: PollingOptions,
    ): Flow<Result<RESPONSE_DATA>> {
        return flow {
            var pollingRetry = 0
            while (pollingOptions.isNeedContinue(pollingRetry)) {
                val requestResult = makeNetworkRequestWithServerResult(networkHttpRequest, data)
                if (requestResult.getOrNull() != null) {
                    emit(requestResult)
                } else {
                    val exception = requestResult.exceptionOrNull() ?: IllegalStateException("Request was failed, but exception is null")
                    when (pollingOptions.failedRequestPolicy) {
                        PollingOptions.FailedRequestPolicy.RETURN_ERROR_AND_CONTINUE -> emit(Result.failure(exception))
                        PollingOptions.FailedRequestPolicy.SKIP_ERROR_AND_CONTINUE -> {}
                        PollingOptions.FailedRequestPolicy.RETURN_ERROR_AND_CANCEL -> {
                            emit(Result.failure(exception))
                            currentCoroutineContext().cancel()
                        }

                        PollingOptions.FailedRequestPolicy.RETURN_ERROR_AND_FINISH -> {
                            emit(Result.failure(exception))
                            return@flow
                        }
                    }
                }
                pollingRetry++
                delay(pollingOptions.delayBetweenRequests)
            }
        }
    }

    public fun NetworkHttpRequest.Method.toKtorMethod(): HttpMethod {
        return when (this) {
            NetworkHttpRequest.Method.POST -> HttpMethod.Post
            NetworkHttpRequest.Method.GET -> HttpMethod.Get
            NetworkHttpRequest.Method.PUT -> HttpMethod.Put
            NetworkHttpRequest.Method.DELETE -> HttpMethod.Delete
        }
    }

    /**
     * Выполняем блок кода, пропуская отмену корутины, если происходила на участке.
     * Позволяет не учитывать пользовательскую отмену корутины во время поиска (с дебаунсом) или отмены действия
     */
    public inline fun <T, R> T.runCatchingExceptCancellation(block: T.() -> R): Result<R> {
        return try {
            Result.success(block())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}