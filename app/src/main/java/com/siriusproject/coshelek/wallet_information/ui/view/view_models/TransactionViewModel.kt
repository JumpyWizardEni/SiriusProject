package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.domain.GetCategories
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

class TransactionViewModel(
    private val getCategoriesUseCase: GetCategories
) : ViewModel() {

    var transactionModel: TransactionUiModel? = null

    fun onCategoryNextButton() {
        TODO("Not yet implemented")
    }

    fun getCategories() = getCategoriesUseCase()
}