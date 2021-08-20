package com.siriusproject.coshelek.wallet_information.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.ui.view.TransactionAddingActivityContract.Companion.RESULT_TAG
import com.siriusproject.coshelek.wallet_information.ui.view.fragments.OperationChangeFragment
import java.time.LocalDateTime

class TransactionAddingActivity : AppCompatActivity() {

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_adding)

        viewModel.transactionModel = TransactionUiModel(
            0,
            "1",
            CategoryUiModel("Зп"),
            TransactionType.Income,
            30,
            "",
            LocalDateTime.now()
        )
        supportFragmentManager.commit {
            replace<OperationChangeFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun finishWithResult() {
        val dataIntent = Intent().apply { putExtra(RESULT_TAG, viewModel.transactionModel) }
        setResult(Activity.RESULT_OK, dataIntent)
        finish()
    }
}