package com.siriusproject.coshelek

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val addButton = findViewById<Button>(R.id.add_operation)

        addButton.setOnClickListener {
            Toast.makeText(this, resources.getString(R.string.op_added), Toast.LENGTH_SHORT).show()
        }
    }
}