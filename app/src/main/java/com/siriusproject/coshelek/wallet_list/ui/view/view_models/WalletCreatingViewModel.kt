package com.siriusproject.coshelek.wallet_list.ui.view.view_models

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
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

    val walletCreated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun onNameReadyPressed(name: String, previosId: Int) {
        Log.d(javaClass.name, "OnNameReadyPressed($name)")
        walletCreated.value = false
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
        walletCreated.value = false
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
            walletRepos.createWallet(walletName.value, currency.value, BigDecimal(0), limit.value)
            walletCreated.value = true
            navigationDispatcher.emit { navController ->
                navController.navigate(R.id.action_walletCreatingInfoFragment_to_walletListFragment)
            }
        }

    }

    fun getWalletInfo(id: Int) {
        currWalletId = id
        viewModelScope.launch {
            walletRepos.getWalletInfo(id, currency.value, true).collect { model ->
                walletName.value = model.name
                currency.value = model.currency
                limit.value = model.limit
            }
        }
    }

    fun onEditWalletPressed() {
        viewModelScope.launch {
            walletRepos.changeWallet(currWalletId, walletName.value, null, null, null)
            walletCreated.value = true
            navigationDispatcher.emit { navController ->
                navController.navigate(R.id.action_walletChangingFragment_to_walletListFragment)
            }
        }
    }

}