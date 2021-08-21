package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val getCategoriesUseCase: GetCategories
) : ViewModel() {

    private val getCategoriesUseCase = GetCategories(CategoriesMapper())

    var amount: String? = null
    var type: TransactionType? = null
    var category: String? = null

    var transactionModel: TransactionUiModel? = null

    fun onCategoryNextButton() {
        TODO("Not yet implemented")
    }

    fun getCategories() = getCategoriesUseCase()
}