package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


    @HiltViewModel
class WalletViewModel @Inject constructor(
    private val addTransactionUseCase: GetTransactionUseCase
) :
    ViewModel() {

    private val transactionsList = mutableListOf<TransactionUiModel>()
    val transactions = MutableLiveData<List<TransactionUiModel>>()

    init {
        transactionsList.addAll(addTransactionUseCase.getTransactions().toMutableList())
        transactions.value = transactionsList
    }

    //Не факт, что будет передаваться прям моделька, возможно только параметры
    fun addNewTransaction(model: TransactionUiModel) {

        //TODO здесь должно быть добавление транзакции через use_case
        transactionsList.add(model)
        transactions.value = transactionsList
    }
}