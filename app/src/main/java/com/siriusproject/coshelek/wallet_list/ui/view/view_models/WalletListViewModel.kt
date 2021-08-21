package com.siriusproject.coshelek.wallet_list.ui.view.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class WalletListViewModel @Inject constructor(
    private val repos: WalletsRepository
) : ViewModel() {

    val wallets = MutableStateFlow<List<WalletUiModel>>(listOf())
    val mainBalance = MutableStateFlow<BigDecimal>(BigDecimal(0))
    val income = MutableStateFlow<BigDecimal>(BigDecimal(0))
    val expense = MutableStateFlow<BigDecimal>(BigDecimal(0))

    init {
        getWallets()
    }

    fun getWallets() {
        viewModelScope.launch {
            repos.getWallets().collect {
                wallets.value = it
                mainBalance.value = wallets.value.sumOf { it.balance }
                income.value = wallets.value.sumOf { it.income }
                expense.value = wallets.value.sumOf { it.expense }
            }

        }
    }

}