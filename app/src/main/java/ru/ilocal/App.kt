package ru.ilocal

import android.app.Application
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import ru.ilocal.views.MainActivity

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
        VK.addTokenExpiredHandler(tokenTracker)
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            MainActivity.startFrom(this@App)
        }
    }
}
