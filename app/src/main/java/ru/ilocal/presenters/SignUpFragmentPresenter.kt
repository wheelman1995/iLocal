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
        viewState.googlelogin()
    }

    fun onConnectionSuspended(){
        Log.i(TAG, "onConnectionSuspended()")
        viewState.connectGoogleApiClient()
    }

    fun onStartFragment(){
        Log.i(TAG, "onStartFragment()")
        viewState.connectGoogleApiClient()
    }

    fun onStopFragment(){
        Log.i(TAG, "onStopFragment()")
        viewState.disconnectGoogleApiClient()
    }

}