package com.siriusproject.coshelek.wallet_list.ui.view.view_models

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_list.data.remote.Result
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import com.siriusproject.coshelek.wallet_list.ui.view.LoadingState
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.net.ConnectException
import javax.inject.Inject


@HiltViewModel
class WalletListViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val repos: WalletsRepository
) : ViewModel() {

    companion object {
        const val PREVIOUS_FRAGMENT = "previous_fragment"
    }

    val wallets = MutableStateFlow<List<WalletUiModel>>(listOf())
    val mainBalance = MutableStateFlow(BigDecimal(0))
    val income = MutableStateFlow(BigDecimal(0))
    val expense = MutableStateFlow(BigDecimal(0))
    val loadingState = MutableStateFlow(LoadingState.Loading)

    init {
        fetchWallets()
    }

    fun fetchWallets() {
        viewModelScope.launch {
            loadingState.value = LoadingState.Loading
            repos.getWallets().collect {
                when (it) {
                    is LoadResult.Success -> {
                        loadingState.value = LoadingState.Ready
                        wallets.value = it.data!!
                        mainBalance.value = wallets.value.sumOf { it.balance }
                        income.value = wallets.value.sumOf { it.income }
                        expense.value = wallets.value.sumOf { it.expense }
                    }
                    is LoadResult.Loading -> {
                        loadingState.value = LoadingState.Loading
                    }
                    is LoadResult.NoConnection -> {
                        loadingState.value = LoadingState.NoConnection
                        Log.e(javaClass.name, it.toString())
                    }
                    is LoadResult.Error -> {
                        loadingState.value = LoadingState.UnexpectedError
                        Log.e(javaClass.name, "NoConnection: $it")
                    }
                }

            }

        }
    }

    fun onCreateWalletPressed() {
        navigationDispatcher.emit { navController ->
            Log.d(javaClass.name, "Creating wallet...")
            val data = Bundle()
            data.putInt(
                PREVIOUS_FRAGMENT, R.layout.fragment_wallet_list
            )
            navController.navigate(
                R.id.action_walletListFragment_to_walletNameFragment, data
            )
        }
    }


    fun deleteWallet(id: Int) {
        viewModelScope.launch {
            Log.d(javaClass.name, "Deleting wallet...")
            try {
                repos.deleteWallet(id)
            } catch (e: Exception) {
                if (e is ConnectException) {
                    loadingState.value = LoadingState.NoConnection
                } else {
                    loadingState.value = LoadingState.UnexpectedError

                }
            }
            fetchWallets()


        }
    }

    fun onEditWalletPressed(id: Int) {
        navigationDispatcher.emit { navController ->
            Log.d(javaClass.name, "Editing wallet...")
            val data = Bundle()
            data.putInt(WALLET_ID, id)
            navController.navigate(R.id.action_walletListFragment_to_walletChangingFragment, data)
        }
    }

}