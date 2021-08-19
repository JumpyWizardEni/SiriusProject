package com.siriusproject.coshelek.wallet_information.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.ActivityMainBinding
import com.siriusproject.coshelek.wallet_information.ui.adapters.TransactionsAdapter
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel

class MainScreenActivity : AppCompatActivity(R.layout.activity_main) {

    private var previousBackPressedTime: Long = 0
    private val recyclerAdapter = TransactionsAdapter()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: WalletViewModel by viewModels()
    private val activityTransactionAddingLauncher =
        registerForActivityResult(TransactionAddingActivityContract()) { result ->
            // adding transaction to recycler
            if (result != null) {
                viewModel.addNewTransaction(result)
            }
        }

    private fun showRecordsText(isRecyclerEmpty: Boolean) {
        if (isRecyclerEmpty) {
            binding.noRecordsYet.visibility = View.VISIBLE
        } else {
            binding.noRecordsYet.visibility = View.GONE
        }
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
        val addButton = binding.addOperation
        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.VERTICAL, false
            )
        }
        addButton.setOnClickListener {
            activityTransactionAddingLauncher.launch(Unit)
        }
        viewModel.transactions.observe(this, Observer {
            recyclerAdapter.setTransactions(it)
            showRecordsText(recyclerAdapter.itemCount == 0)
        })
    }

    companion object {
        const val BACK_PRESS_TIME = 3000L
        const val ARG_INPUT_KEY = "my_input_key"
        const val RESULT_KEY = "my_result_key"
    }
}