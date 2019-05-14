package ru.ilocal.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import ru.ilocal.R
import ru.ilocal.presenters.SignUpFragmentPresenter

class SignUpFragment : MvpAppCompatFragment(), KodeinAware, SignUpFragmentView {

    override val kodein by kodein()
    @InjectPresenter internal lateinit var presenter: SignUpFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return rootView
    }

    @ProvidePresenter
    internal fun providePresenter() = SignUpFragmentPresenter(kodein)
}