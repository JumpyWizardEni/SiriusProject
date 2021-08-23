package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetCategories
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories
) : ViewModel() {

    var transactionModel: TransactionUiModel? = null

    fun onCategoryNextButton() {
        TODO("Not yet implemented")
    }

    fun getCategories() = getCategoriesUseCase()
}