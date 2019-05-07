package ru.ilocal

import android.app.Application
import android.content.Context
import android.util.Log
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.ActivityRetainedScope
import org.kodein.di.android.kodein
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.Scope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

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
