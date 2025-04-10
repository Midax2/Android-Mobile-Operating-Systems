package com.pg.service

import androidx.lifecycle.LifecycleService
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*

class LifeTimeService : LifecycleService() {
    private val binder = LocalBinder()
    private var secondsAfterCreationPassed: Int = 0
    private var serviceJob: Job? = null
    private var callback: ServiceCallback? = null

    inner class LocalBinder : Binder() {
        fun getService(): LifeTimeService = this@LifeTimeService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        serviceJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(1000)
                secondsAfterCreationPassed++
                callback?.onGetResult(secondsAfterCreationPassed)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob?.cancel()
    }

    interface ServiceCallback {
        fun onGetResult(intResult: Int)
    }

    fun obtainCallback(callback: ServiceCallback?) {
        this.callback = callback
    }
}
