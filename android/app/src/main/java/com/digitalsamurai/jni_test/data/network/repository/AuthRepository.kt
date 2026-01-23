package com.digitalsamurai.jni_test.data.network.repository

import android.content.Context
import com.digitalsamurai.core.otel.extensions.withTracedContext
import com.digitalsamurai.opentelemetry.example.core.network.models.Jwt
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * ТАК ДЕЛАТЬ НИКОГДА НЕ НАДО!!! НЕ СТОИТ ХРАНИТЬ АВТОРИЗАЦИОННЫЕ ТОКЕНЫ В НЕЗАШИФРОВАННОМ ХРАНИЛИЩЕ
 */
class AuthRepository @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context,
) {

    private val preferences = applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    @Volatile
    private var cache: Jwt? = null

    suspend fun set(jwt: Jwt) = withTracedContext("AuthRepository.set") {
        preferences.edit().putString(PREFERENCE_KEY, jwt.value).also {
            cache = jwt
        }
    }

    suspend fun get(): Jwt? = withTracedContext("AuthRepository.get") {
        return@withTracedContext cache ?: preferences.getString(PREFERENCE_KEY, null)?.let {
            cache = Jwt(it)
            cache
        }
    }

    fun isTokenExist(): Boolean {
        return (cache ?: preferences.getString(PREFERENCE_KEY, null)) != null
    }

    private companion object {
        const val PREFERENCE_NAME = "secret"
        const val PREFERENCE_KEY  = "auth_key"
    }
}