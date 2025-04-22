package com.digitalsamurai.core.otel

import android.app.Application
import android.util.Log

abstract class OtelApplication: Application() {

    private var sessionId: String? = null
    private var otel: Otel? = null

    override fun onCreate() {
        initOtel(DEFAULT_SESSION_ID)
        super.onCreate()
    }
    fun onCreate(sessionId: String) {
        this.sessionId = sessionId
        initOtel(sessionId)
        super.onCreate()
    }

    private fun initOtel(sessionId: String) {
        if (isProcessForOtel()) {
            otel = Otel(this, sessionId)
            if(otel?.initOtel() != true) {
                Log.e("OBAMA", "FAIL INIT OTEL")
            }
        }
    }

    /**
     * launch otel only in that process, which we need
     */
    abstract fun isProcessForOtel(): Boolean

    private companion object {
        const val DEFAULT_SESSION_ID = ""
    }
}