package com.siriusproject.coshelek

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.siriusproject.coshelek.data.model.CategoryUiModel
import com.siriusproject.coshelek.data.model.TransactionType
import com.siriusproject.coshelek.data.model.TransactionUiModel
import com.siriusproject.coshelek.ui.view.TransactionViewModel
import com.siriusproject.coshelek.ui.view.fragments.OperationChangeFragment
import java.util.*

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
            Date(System.currentTimeMillis())
        )
        supportFragmentManager.commit {
            replace<OperationChangeFragment>(R.id.fragment_container)
        }
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}