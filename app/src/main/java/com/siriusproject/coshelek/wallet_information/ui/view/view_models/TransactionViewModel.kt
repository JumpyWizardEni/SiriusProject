package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories,
    private val repos: TransactionsRepository
) : ViewModel() {

    lateinit var amount: String
    lateinit var type: TransactionType
    lateinit var category: String

    fun getCategories() = getCategoriesUseCase()
}