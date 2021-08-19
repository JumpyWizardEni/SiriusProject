package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetTransactionUseCase

class WalletViewModel() : ViewModel() {


    //TODO DI
    //DI будет решаться в другой ветке, пока напрямую
    private val addTransactionUseCase = GetTransactionUseCase()

    private val transactionsData: MutableLiveData<List<TransactionUiModel>> = MutableLiveData()
    private var transactionsList = mutableListOf<TransactionUiModel>()
    val transactions: LiveData<List<TransactionUiModel>> get() = transactionsData

    init {
        transactionsList = addTransactionUseCase.getTransactions().toMutableList()
        transactionsData.value = transactionsList
    }

    //Не факт, что будет передаваться прям моделька, возможно только параметры
    fun addNewTransaction(model: TransactionUiModel) {

        //TODO здесь должно быть добавление транзакции через use_case
        transactionsList.add(model)
        transactionsData.value = transactionsList
    }
}