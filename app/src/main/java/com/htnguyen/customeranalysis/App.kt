package com.htnguyen.customeranalysis

import android.app.Application
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
    }

}