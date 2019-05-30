package ru.ilocal.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import ru.ilocal.R
import ru.ilocal.presenters.SignUpFragmentPresenter

class SignUpFragment : MvpAppCompatFragment(), KodeinAware, SignUpFragmentView {

    override val kodein by kodein()
    @InjectPresenter internal lateinit var presenter: SignUpFragmentPresenter

    private lateinit var vkAuthButton: ImageButton
    private lateinit var googleAuthButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up, container, false)

        vkAuthButton = rootView.findViewById(R.id.vk_auth_button)
        googleAuthButton = rootView.findViewById(R.id.google_auth_button)

        vkAuthButton.setOnClickListener {
            presenter.onClickVkAuthButton()
        }

        googleAuthButton.setOnClickListener {
            presenter.onClickGoogleAuthButton()
        }

        return rootView
    }

    override fun vklogin() {
        VK.login(activity as Activity, arrayListOf(VKScope.EMAIL, VKScope.STATUS))
    }

    @ProvidePresenter
    internal fun providePresenter() = SignUpFragmentPresenter(kodein)
}