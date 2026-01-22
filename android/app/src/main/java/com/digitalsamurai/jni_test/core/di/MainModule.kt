package com.digitalsamurai.jni_test.core.di

import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideNetworkHttpClient(): NetworkHttpClient {
        return NetworkHttpClient(
            hostAddress = "10.0.2.2",
            portAddress = 8080,
            otel = Otel,
        )
    }
}