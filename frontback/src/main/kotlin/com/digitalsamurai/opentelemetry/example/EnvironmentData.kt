package com.digitalsamurai.opentelemetry.example

data class EnvironmentData(
    val server: ServerData,
    val otel: OtelData,
) {
    data class ServerData(
        val host: String,
        val port: Int
    )

    data class OtelData(
        val host: String,
        val port: Int,
    )
    companion object {
        fun fromArgsOrSystemEnv(args: Array<String>): EnvironmentData {
            val serverHost = args.getOrNull(args.indexOf("server_host") + 1) ?: System.getenv("server_host")
            val serverPort = (args.getOrNull(args.indexOf("server_port") + 1) ?: System.getenv("server_port")).toInt()

            val otelHost = args.getOrNull(args.indexOf("otel_host") + 1) ?: System.getenv("otel_host")
            val otelPort = (args.getOrNull(args.indexOf("otel_port") + 1) ?: System.getenv("otel_port")).toInt()

            return EnvironmentData(
                server = ServerData(
                    host = serverHost,
                    port = serverPort
                ),
                otel = OtelData(
                    host = otelHost,
                    port = otelPort
                )
            )
        }
    }
}