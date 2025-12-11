package com.digitalsamurai.jni_test.di

import android.content.Context
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.GeneratorApplication
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
}