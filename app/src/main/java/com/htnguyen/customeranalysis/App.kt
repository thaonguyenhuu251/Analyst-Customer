package com.htnguyen.customeranalysis

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.htnguyen.customeranalysis.ultils.Event
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.HashMap

class App : Application() {
    companion object {
        val eventBus: PublishSubject<HashMap<String, Any>> by lazy { PublishSubject.create() }
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    }
    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()
        disposable = eventBus.subscribe{
            it[Event.EVENT_CHANGE_BACKGROUND]?.let { data->

            }
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

}