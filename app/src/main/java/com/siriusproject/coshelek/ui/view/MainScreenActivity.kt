package com.siriusproject.coshelek.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.TransactionAddingActivity
import com.siriusproject.coshelek.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    companion object {
        const val BACK_PRESS_TIME = 3000L
    }

    var previousBackPressedTime: Long = 0

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
            Toast.makeText(this, resources.getString(R.string.op_added), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TransactionAddingActivity::class.java)
            startActivity(intent)
        }
    }

}