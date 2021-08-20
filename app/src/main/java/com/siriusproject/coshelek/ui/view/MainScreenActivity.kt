package com.siriusproject.coshelek.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.ActivityMainBinding

class MainScreenActivity : AppCompatActivity() {

    private var previousBackPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val BACK_PRESS_TIME = 3000L
        const val ARG_INPUT_KEY = "my_input_key"
        const val RESULT_KEY = "my_result_key"
    }
}