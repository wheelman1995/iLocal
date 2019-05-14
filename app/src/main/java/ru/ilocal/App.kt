package ru.ilocal

import android.app.Application
import android.util.Log
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

fun <T : Any> T.logd(obj: Any?) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.simpleName, "$obj")
    }
}

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@App))

    }

    override fun onCreate() {
        super.onCreate()
    }
}
