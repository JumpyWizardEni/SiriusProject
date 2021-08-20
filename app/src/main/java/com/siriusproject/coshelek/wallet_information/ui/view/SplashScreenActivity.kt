package com.siriusproject.coshelek.wallet_information.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            Log.d(javaClass.toString(), "Token: ${account.idToken}, Email: ${account.email}")
            startMainScreen()
        } else {
            startOnBoard()
        }
        finish()
    }

    private fun startOnBoard() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
    }

    private fun startMainScreen() {
        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
    }
}