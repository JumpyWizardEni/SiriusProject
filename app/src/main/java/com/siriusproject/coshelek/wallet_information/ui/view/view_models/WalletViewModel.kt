package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_list.ui.view.LoadingState
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repos: TransactionsRepository,
    private val navigationDispatcher: NavigationDispatcher
) :
    ViewModel() {

    private val transactionsData = MutableStateFlow<List<TransactionUiModel>>(listOf())
    val transactions: StateFlow<List<TransactionUiModel>> = transactionsData
    private val loadingStateData = MutableStateFlow(LoadingState.Loading)
    val loadingState: StateFlow<LoadingState> = loadingStateData
    var walletId: Int = 0

    fun fetchTransactions() {
        viewModelScope.launch {
            loadingStateData.value = LoadingState.Loading
            repos.getTransactions(walletId, 5, 0).collect {
                when (it) {
                    is LoadResult.Success -> {
                        loadingStateData.value = LoadingState.Ready
                        transactionsData.value = it.data!!
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
            navController.navigate(R.id.action_walletFragment_to_enterSumFragment, data)
        }
    }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            repos.deleteTransaction(id)
            fetchTransactions()

        }
    }

    fun onEditWalletPressed(id: Int) {
        //TODO
    }
}