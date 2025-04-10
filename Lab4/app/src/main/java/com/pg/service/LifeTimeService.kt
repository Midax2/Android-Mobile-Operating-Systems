package com.pg.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

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
        val chanelId = "my_channel_01"
        val channel = NotificationChannel(chanelId, "My Channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        (getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager).
        createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(this,
            chanelId).build()
        ServiceCompat.startForeground(this, 101, notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)

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
