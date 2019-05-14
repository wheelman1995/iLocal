package ru.ilocal.views

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import ru.ilocal.R
import ru.ilocal.presenters.MainActivityPresenter

class MainActivity : MvpAppCompatActivity(), KodeinAware, MainActivityView {

    override val kodein by kodein()
    @InjectPresenter internal lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    @ProvidePresenter
    internal fun providePresenter() = MainActivityPresenter(kodein)

}
