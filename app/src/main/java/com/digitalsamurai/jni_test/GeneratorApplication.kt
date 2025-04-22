package com.digitalsamurai.jni_test

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import java.util.UUID

@HiltAndroidApp
class GeneratorApplication: Application() {

    override fun onCreate() {
        if (isMainProcess()) {
            val sessionId = generateSessionId()
            Log.d("OBAMA", "SessionId: $sessionId")
        }
        super.onCreate()
    }

    /**
     * Generate core session id for observability per app launching
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
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager;
        val list = activityManager.runningAppProcesses;
        val pid = android.os.Process.myPid()
        val processName = list.find { it.pid == pid }
        return processName?.processName
    }
}