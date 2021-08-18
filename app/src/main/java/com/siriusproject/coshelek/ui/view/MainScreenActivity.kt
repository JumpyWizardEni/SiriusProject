package com.siriusproject.coshelek.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.TransactionAddingActivity
import com.siriusproject.coshelek.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    private var previousBackPressedTime: Long = 0

    private val activityTransactionAddingLauncher =
        registerForActivityResult(TransactionAddingActivityContract()) { result ->
            // adding transaction to recycler
        }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - previousBackPressedTime < BACK_PRESS_TIME) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, resources.getString(R.string.press_back), Toast.LENGTH_LONG)
                .show()
        }
        previousBackPressedTime = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addButton = binding.addOperation

        addButton.setOnClickListener {
            activityTransactionAddingLauncher.launch(Unit)
            val intent = Intent(this, TransactionAddingActivity::class.java)
            startActivity(intent)
        }

    }

    companion object {
        const val BACK_PRESS_TIME = 3000L
        const val ARG_INPUT_KEY = "my_input_key"
        const val RESULT_KEY = "my_result_key"
    }
}