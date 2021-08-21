package com.siriusproject.coshelek.wallet_list.ui.view.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.wallet_list.domain.use_cases.CreateWalletUseCase
import com.siriusproject.coshelek.wallet_list.domain.use_cases.GetWalletsUseCase
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WalletListViewModel @Inject constructor(
    private val createWalletUseCase: CreateWalletUseCase,
    private val getWalletsUseCase: GetWalletsUseCase
) : ViewModel() {

    val wallets = MutableLiveData<List<WalletUiModel>>()

    init {
        getWallets()
    }

    fun getWallets() {
        viewModelScope.launch {
            wallets.value = getWalletsUseCase.getWallets()
        }
    }

    fun getMainBalance() = wallets.value?.sumOf { it.balance }

    fun getMainIncome() = wallets.value?.sumOf { it.income }

    fun getMainExpense() = wallets.value?.sumOf { it.expense }
}