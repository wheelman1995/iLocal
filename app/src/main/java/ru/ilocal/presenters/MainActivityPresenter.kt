package ru.ilocal.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.kodein.di.Kodein
import ru.ilocal.views.MainActivityView

@InjectViewState
class MainActivityPresenter(private val kodein: Kodein) : MvpPresenter<MainActivityView>()