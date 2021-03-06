package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.checkOperation
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repos: TransactionsRepository,
    private val walletRepos: WalletsRepository,
    private val navigationDispatcher: NavigationDispatcher
) :
    ViewModel() {

    companion object {
        const val PAGE_SIZE = 15
        const val TRANSACTION = "transaction"
    }

    private var totalNumberOfItems: Long = 0
    private var previousReceivedCount = PAGE_SIZE
    private val transactionsData = MutableStateFlow<List<TransactionUiModel>>(listOf())
    private val loadingStateData = MutableStateFlow(LoadingState.Initial)
    private val balanceData = MutableStateFlow(BigDecimal.ZERO)
    private val incomeData = MutableStateFlow(BigDecimal.ZERO)
    private val expenceData = MutableStateFlow(BigDecimal.ZERO)
    private val limitReachedData = MutableStateFlow(false)
    private var pageNumber = 0
    private val currencyData = MutableStateFlow<String?>(null)
    private var isTransactionsFetching = false
    val currency = currencyData as StateFlow<String?>
    val transactions: StateFlow<List<TransactionUiModel>> = transactionsData
    var firstCollect: Boolean = true
    val loadingState: StateFlow<LoadingState> = loadingStateData
    val limitReached: StateFlow<Boolean> = limitReachedData
    val isAllLoaded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var walletId: Int = 0
    val balance: StateFlow<BigDecimal> = balanceData
    val income: StateFlow<BigDecimal> = incomeData
    val expence: StateFlow<BigDecimal> = expenceData

    fun fetchTransactions() {

        pageNumber = 0
        isAllLoaded.value = false
        isTransactionsFetching = true
        viewModelScope.launch {
            loadingStateData.value = LoadingState.Loading
            repos.getTransactions(walletId, PAGE_SIZE, pageNumber).collect {
                when (it) {
                    is LoadResult.Success -> {
                        pageNumber++
                        previousReceivedCount = it.data.first.size
                        loadingStateData.value = LoadingState.Ready
                        totalNumberOfItems = it.data.second
                        transactionsData.value = it.data.first
                        isAllLoaded.value = (it.data.first.size.toLong() == totalNumberOfItems)

                    }
                    is LoadResult.Loading -> {
                        loadingStateData.value = LoadingState.Loading
                    }
                    is LoadResult.NoConnection -> {
                        loadingStateData.value = LoadingState.NoConnection
                        Log.e(javaClass.name, it.toString())
                    }
                    is LoadResult.Error -> {
                        loadingStateData.value = LoadingState.UnexpectedError
                        Log.e(javaClass.name, "NoConnection: $it")
                    }
                }
                isTransactionsFetching = false
            }

        }
    }

    fun loadNewPage() {
        if (isAllLoaded.value || isTransactionsFetching) {
            return
        }
        viewModelScope.launch {
            Log.d(javaClass.name, "Loading new page")
            repos.getTransactions(walletId, PAGE_SIZE, pageNumber).collect {
                when (it) {
                    is LoadResult.Success -> {
                        pageNumber++
                        val newList = mutableListOf<TransactionUiModel>()
                        newList.addAll(transactionsData.value)
                        newList.addAll(it.data.first)
                        previousReceivedCount = it.data.first.size
                        totalNumberOfItems = it.data.second
                        transactionsData.value = newList
                        isAllLoaded.value =
                            (transactionsData.value.size.toLong() == totalNumberOfItems)
                        loadingStateData.value = LoadingState.Ready

                    }
                    is LoadResult.Loading -> {
                        loadingStateData.value = LoadingState.Loading
                    }
                    is LoadResult.NoConnection -> {
                        loadingStateData.value = LoadingState.NoConnection
                        Log.e(javaClass.name, it.toString())
                    }
                    is LoadResult.Error -> {
                        loadingStateData.value = LoadingState.UnexpectedError
                        Log.e(javaClass.name, "NoConnection: $it")
                    }
                }

            }
        }
    }

    fun onAddOperationClicked(id: Int) {
        navigationDispatcher.emit { navController ->
            Log.d(javaClass.name, "Creating operation...")
            val data = Bundle()
            data.putInt(WalletListFragment.WALLET_ID, id)
            data.putInt(PREVIOUS_FRAGMENT, R.layout.fragment_wallet)
            navController.navigate(R.id.action_walletFragment_to_enterSumFragment, data)
        }
    }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            checkOperation(loadingStateData) {
                repos.deleteTransaction(id)
                getWalletInfo(walletId)
                transactionsData.value = transactions.value.filter {
                    it.id != id
                }
            }
        }
    }

    fun onEditWalletPressed(model: TransactionUiModel, walletId: Int) {
        navigationDispatcher.emit { navController ->
            Log.d(javaClass.name, "Creating operation...")
            val data = Bundle()
            data.putSerializable(TRANSACTION, model)
            data.putInt(WALLET_ID, walletId)
            navController.navigate(R.id.action_walletFragment_to_operationEditFragment, data)
        }
    }

    fun getWalletInfo(walletId: Int) {
        viewModelScope.launch {
            limitReachedData.value = false
            checkOperation(loadingStateData) {
                walletRepos.getWalletInfo(walletId).collect {
                    balanceData.value = it.balance
                    incomeData.value = it.income
                    expenceData.value = it.expense
                    currencyData.value = it.currency
                    limitReachedData.value = it.isLimitReached
                }

            }
        }
    }

}


