package com.siriusproject.coshelek

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.siriusproject.coshelek.data.model.TransactionUiModel

class TransactionAddingActivityContract : ActivityResultContract<Unit, TransactionUiModel?>() {

    companion object {
        const val RESULT_TAG = "Model"
    }

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, TransactionAddingActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): TransactionUiModel? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getSerializableExtra(RESULT_TAG) as TransactionUiModel?
    }

}