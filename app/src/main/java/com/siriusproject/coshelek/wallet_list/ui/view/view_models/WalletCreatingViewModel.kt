package com.siriusproject.coshelek.wallet_list.ui.view.view_models

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.checkOperation
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class WalletCreatingViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val walletRepos: WalletsRepository
) : ViewModel() {


    private var currWalletId = 0
    val walletName = MutableStateFlow("")
    val currency = MutableStateFlow("Российский рубль")
    val limit = MutableStateFlow(BigDecimal.ZERO)
    val loadingState = MutableStateFlow(LoadingState.Initial)
    fun onNameReadyPressed(name: String, previosId: Int) {
        Log.d(javaClass.name, "OnNameReadyPressed($name)")
        walletName.value = name
        when (previosId) {
            R.layout.fragment_wallet_list, R.layout.fragment_wallet_creating_info -> navigationDispatcher.emit { navController ->
                Log.d(javaClass.name, "Navigating to WalletCreatingInfoFragment")
                navController.navigate(R.id.action_walletNameFragment_to_walletCreatingInfoFragment)
            }
            else ->
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_walletNameFragment_to_walletChangingFragment)
                }
        }

    }

    fun onWalletNamePressed(fragmentLayout: Int) {
        Log.d(javaClass.name, "OnWalletNamePressed")
        val data = Bundle()
        data.putInt(PREVIOUS_FRAGMENT, fragmentLayout)
        navigationDispatcher.emit { navController ->
            when (fragmentLayout) {
                R.layout.fragment_wallet_creating_info -> navController.navigate(
                    R.id.action_walletCreatingInfoFragment_to_walletNameFragment,
                    data
                )
                R.layout.fragment_wallet_changing -> navController.navigate(
                    R.id.action_walletChangingFragment_to_walletNameFragment,
                    data
                )
            }

        }
    }

    fun onCreateWalletPressed() {
        Log.d(javaClass.name, "OnCreateWalletPressed")
        viewModelScope.launch {
            checkOperation(loadingState) {
                loadingState.value = LoadingState.Loading
                walletRepos.createWallet(
                    walletName.value,
                    currency.value,
                    BigDecimal(0),
                    limit.value
                )
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_walletCreatingInfoFragment_to_walletListFragment)
                }
            }
        }

    }

    fun getWalletInfo(id: Int) {
        currWalletId = id
        viewModelScope.launch {
            walletRepos.getWalletInfo(id).collect { model ->
                walletName.value = model.name
                currency.value = model.currency
                limit.value = model.limit
            }

        }
    }

    fun onEditWalletPressed() {
        viewModelScope.launch {
            checkOperation(loadingState) {
                walletRepos.changeWallet(currWalletId, walletName.value, null, limit.value, null)
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_walletChangingFragment_to_walletListFragment)
                }
            }
        }
    }

    fun onLimitReadyPressed(newLimit: BigDecimal, previousId: Int) {
        limit.value = newLimit
        when (previousId) {
            R.layout.fragment_wallet_list, R.layout.fragment_wallet_creating_info -> navigationDispatcher.emit { navController ->
                Log.d(javaClass.name, "Navigating to WalletCreatingInfoFragment")
                navController.navigate(R.id.action_walletLimitFragment_to_walletCreatingInfoFragment)
            }
            else ->
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_walletLimitFragment_to_walletChangingFragment)
                }
        }
    }

    fun onLimitPressed(fragmentLayout: Int) {
        val data = Bundle()
        data.putInt(PREVIOUS_FRAGMENT, fragmentLayout)
        navigationDispatcher.emit { navController ->
            when (fragmentLayout) {
                R.layout.fragment_wallet_creating_info -> navController.navigate(
                    R.id.action_walletCreatingInfoFragment_to_walletLimitFragment,
                    data
                )
                R.layout.fragment_wallet_changing -> navController.navigate(
                    R.id.action_walletChangingFragment_to_walletLimitFragment,
                    data
                )
            }

        }
    }

}