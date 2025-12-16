package com.digitalsamurai.jni_test.di

import android.content.Context
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.BuildConfig
import com.digitalsamurai.jni_test.GeneratorApplication
import com.digitalsamurai.opentelemetry.example.core.network.NetworkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {


    @Provides
    @Singleton
    fun provideOtel(@ApplicationContext context: Context): Otel {
        return (context.applicationContext as GeneratorApplication).otel
    }

    @Provides
    @Singleton
    fun provideNetworkHttpClient(
        otel: Otel,
    ): NetworkHttpClient {
        return NetworkHttpClient(
            hostAddress = "10.0.2.2",
            portAddress = 8080,
            otel = otel,
        )
    }
}