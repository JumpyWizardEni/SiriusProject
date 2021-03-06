package com.siriusproject.coshelek.wallet_information.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.utils.GoogleAuthRepository
import com.siriusproject.coshelek.wallet_list.ui.view.WalletListActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    @Inject
    lateinit var googleAuthRepository: GoogleAuthRepository

    private val loginResultHandler =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(javaClass.toString(), result.toString())
            val task = GoogleSignIn.getSignedInAccountFromIntent(result?.data)

            task.addOnCompleteListener {
                try {
                    val account = task.result
                    if (account != null) {
                        Log.d(javaClass.toString(), "Google Token: ${account.idToken}")
                        account.email?.let { googleAuthRepository.email = it }
                        account.idToken?.let { googleAuthRepository.token = it }
                        startWalletActivity()
                    } else {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.auth_failed),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.loading_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val googleButton = findViewById<Button>(R.id.authButton)

        googleButton.setOnClickListener {
            loginResultHandler.launch(getSignInIntent())
        }
    }

    private fun getSignInIntent(): Intent {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.client_id))
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        return mGoogleSignInClient.signInIntent
    }

    private fun startWalletActivity() {
        val intent = Intent(this, WalletListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        finish()
    }

}