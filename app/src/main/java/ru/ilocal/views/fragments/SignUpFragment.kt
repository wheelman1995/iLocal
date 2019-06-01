package ru.ilocal.views.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import ru.ilocal.R
import ru.ilocal.presenters.SignUpFragmentPresenter

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status

class SignUpFragment : MvpAppCompatFragment(), KodeinAware, SignUpFragmentView, ConnectionCallbacks, OnConnectionFailedListener {

    override val kodein by kodein()
    @InjectPresenter internal lateinit var presenter: SignUpFragmentPresenter

    @ProvidePresenter
    internal fun providePresenter() = SignUpFragmentPresenter(kodein)

    private val TAG: String = "MyLogger"

    private val SIGNED_IN = 0
    private val STATE_SIGNING_IN = 1
    private val STATE_IN_PROGRESS = 2
    private val RC_SIGN_IN = 1111

    private lateinit var vkAuthButton: ImageButton
    private lateinit var googleAuthButton: ImageButton

    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mSignInIntent: PendingIntent? = null
    private  var mSignInProgress: Int = STATE_SIGNING_IN

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

        mGoogleApiClient = buildGoogleApiClient()

        return rootView
    }

    override fun vklogin() {
        VK.login(activity as Activity, arrayListOf(VKScope.EMAIL, VKScope.STATUS))
    }

    override fun googlelogin() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun connectGoogleApiClient() {
        mGoogleApiClient.connect()
    }

    override fun disconnectGoogleApiClient() {
        mGoogleApiClient.disconnect()
    }

    private fun buildGoogleApiClient(): GoogleApiClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .build()

        val googleApiClient = GoogleApiClient.Builder(activity as Activity)
            .addConnectionCallbacks(this@SignUpFragment)
            .addOnConnectionFailedListener(this@SignUpFragment)
            .enableAutoManage(activity as FragmentActivity, this@SignUpFragment)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        return googleApiClient
    }

    override fun onStart() {
        super.onStart()
        presenter.onStartFragment()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStopFragment()
    }

    override fun onConnected(p0: Bundle?) {
        Log.i(TAG, "onConnected()")
        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)

        mSignInProgress = SIGNED_IN

        opr.setResultCallback(object : ResultCallback<GoogleSignInResult> {

            override fun onResult(result: GoogleSignInResult) {
                if (result.isSuccess) {
                    try {
                        /**
                         * Вот здесь приходят email и id от google
                         */
                        val account = result.signInAccount
                        Log.i(TAG, "Google AUTH email: ${account?.email}")
                        Log.i(TAG, "Google AUTH id: ${account?.id}")
                    } catch (ex: Exception) {
                        Log.i(TAG, "Google AUTH: ${ex.localizedMessage}")
                        Log.i(TAG, "Google AUTH: $ex")
                    }

                }

            }
        })
    }

    override fun onConnectionSuspended(p0: Int) {
        presenter.onConnectionSuspended()
    }


    override fun onConnectionFailed(result: ConnectionResult) {
        Log.i(TAG, "onConnectionFailed")
        if (mSignInProgress != STATE_IN_PROGRESS) {
            mSignInIntent = result.resolution
            if (mSignInProgress == STATE_SIGNING_IN) {
                mSignInIntent?.let {
                    try {
                        mSignInProgress = STATE_IN_PROGRESS
                        result.startResolutionForResult(activity,RC_SIGN_IN)
                    } catch (e: IntentSender.SendIntentException) {
                        mSignInProgress = STATE_SIGNING_IN
                        mGoogleApiClient.connect()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "FRAGMENT onActivityResult")
        if(requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "FRAGMENT onActivityResult RESULT_OK)")
                mSignInProgress = STATE_SIGNING_IN
            } else {
                mSignInProgress = SIGNED_IN
            }

            if (!mGoogleApiClient.isConnecting) {
                Log.i(TAG, "FRAGMENT !isConnecting")
                mGoogleApiClient.reconnect()
            }
        }
    }
}