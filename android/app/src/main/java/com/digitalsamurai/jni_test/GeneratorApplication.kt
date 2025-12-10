package com.digitalsamurai.jni_test

import android.app.ActivityManager
import android.content.Context
import com.digitalsamurai.core.otel.OtelApplication
import dagger.hilt.android.HiltAndroidApp
import java.util.UUID

@HiltAndroidApp
class GeneratorApplication : OtelApplication() {

    /**
     * launch otel only in main process
     */
    override fun isProcessForOtel() = isMainProcess()

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * Generate core session id for observability per app launching
     * Not needed now
     */
    private fun generateSessionId(): String {
        val uuid = UUID.randomUUID().toString()
        return "$uuid-${System.currentTimeMillis()}"
    }

    private fun isMainProcess(): Boolean {
        val currentProcess = getCurrentProcess()
        // see AndroidManifest -> application -> process
        return (currentProcess?.split(":")?.getOrNull(1) == "GenerationProcess")
    }

    private fun getCurrentProcess(): String? {
        val activityManager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val list = activityManager.runningAppProcesses
        val pid = android.os.Process.myPid()
        val processName = list.find { it.pid == pid }
        return processName?.processName
    }
}