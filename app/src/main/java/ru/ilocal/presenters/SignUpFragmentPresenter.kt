package ru.ilocal.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.kodein.di.Kodein
import ru.ilocal.views.fragments.SignUpFragmentView

@InjectViewState
class SignUpFragmentPresenter(private val kodein: Kodein) : MvpPresenter<SignUpFragmentView>() {

    private val TAG: String = "MyLogger"

    fun onClickVkAuthButton(){
        Log.i(TAG, "onClickVkAuthButton()")
        viewState.vklogin()
    }

    fun onClickGoogleAuthButton(){
        Log.i(TAG, "onClickGoogleAuthButton()")
    }

}