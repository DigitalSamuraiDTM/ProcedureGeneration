package com.digitalsamurai.jni_test

import android.app.ActivityManager
import android.app.Application
import com.digitalsamurai.core.otel.Otel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GeneratorApplication : Application() {


    /**
     * launch otel only in main process
     */
    fun isProcessForOtel() = isMainProcess()

    override fun onCreate() {
        if (isProcessForOtel()) {
            val isInited = Otel.initOtel(this)
            if (!isInited) error("fatal init otel")
        }
        super.onCreate()
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