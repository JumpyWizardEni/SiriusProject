package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.domain.GetCategories
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

class TransactionViewModel : ViewModel() {
    fun onCategoryNextButton() {
        TODO("Not yet implemented")
    }
    val getCategories = GetCategories()
    var transactionModel: TransactionUiModel? = null
}