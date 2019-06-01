package ru.ilocal.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import ru.ilocal.R
import ru.ilocal.presenters.MainActivityPresenter
import com.vk.api.sdk.utils.VKUtils



class MainActivity : MvpAppCompatActivity(), KodeinAware, MainActivityView {

    private val TAG: String = "MyLogger"

    override val kodein by kodein()
    @InjectPresenter internal lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    @ProvidePresenter
    internal fun providePresenter() = MainActivityPresenter(kodein)

    companion object {
        fun startFrom(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                /**
                 * Из токена VK после авторизации пользователя можно получить id и email
                 */
                Log.i(TAG, token.userId.toString())
                Log.i(TAG, token.email)
            }

            override fun onLoginFailed(errorCode: Int) {
            }
        }
        if (!VK.onActivityResult(requestCode, resultCode, data!!, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
