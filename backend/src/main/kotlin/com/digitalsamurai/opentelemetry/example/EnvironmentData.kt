package com.digitalsamurai.opentelemetry.example

data class EnvironmentData(
    val server: ServerData,
    val services: Services,
) {
    data class ServerData(
        val host: String,
        val port: Int
    )

    data class Services(
        val otel: Otel,
        val auth: Auth
    ) {
        data class Otel(
            val host: String,
            val port: Int,
        )

        data class Auth(
            val host: String,
            val port: Int,
        )
    }

    companion object {
        fun fromArgsOrSystemEnv(args: Array<String>): EnvironmentData {
            val serverHost = args.getOrNull(args.indexOf("server_host") + 1) ?: System.getenv("server_host")
            val serverPort = (args.getOrNull(args.indexOf("server_port") + 1) ?: System.getenv("server_port")).toInt()

            val otelHost = args.getOrNull(args.indexOf("otel_host") + 1) ?: System.getenv("otel_host")
            val otelPort = (args.getOrNull(args.indexOf("otel_port") + 1) ?: System.getenv("otel_port")).toInt()

            val authHost = args.getOrNull(args.indexOf("auth_host") + 1) ?: System.getenv("auth_host")
            val authPort = (args.getOrNull(args.indexOf("auth_port") + 1) ?: System.getenv("auth_port")).toInt()

            return EnvironmentData(
                server = ServerData(
                    host = serverHost,
                    port = serverPort
                ),
                services = Services(
                    otel = Services.Otel(
                        host = otelHost,
                        port = otelPort
                    ),
                    auth = Services.Auth(
                        host = authHost,
                        port = authPort
                    )
                ),
            )
        }
    }
}