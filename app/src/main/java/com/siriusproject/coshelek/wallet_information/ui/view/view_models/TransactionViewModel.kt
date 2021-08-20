package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetCategories
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.CategoriesMapper

class TransactionViewModel(

) : ViewModel() {

    private val getCategoriesUseCase = GetCategories(CategoriesMapper())
    var transactionModel: TransactionUiModel? = null

    fun onCategoryNextButton() {
        TODO("Not yet implemented")
    }

    fun getCategories() = getCategoriesUseCase()
}