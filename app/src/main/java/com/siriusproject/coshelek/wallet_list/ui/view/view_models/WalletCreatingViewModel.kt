package com.siriusproject.coshelek.wallet_list.ui.view.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class WalletCreatingViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val walletRepos: WalletsRepository
) : ViewModel() {

    var walletName = MutableStateFlow("")
    var currency = MutableStateFlow("Российский рубль")
    var limit = MutableStateFlow(BigDecimal.ZERO)

    val walletCreated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun onNameReadyPressed(name: String) {
        Log.d(javaClass.name, "OnNameReadyPressed($name)")
        walletCreated.value = false
        walletName.value = name
        navigationDispatcher.emit { navController ->
            navController.navigate(R.id.action_walletNameFragment_to_walletCreatingInfoFragment)
        }
    }

    fun onWalletNamePressed() {
        Log.d(javaClass.name, "OnWalletNamePressed")
        navigationDispatcher.emit { navController ->
            navController.navigate(R.id.action_walletCreatingInfoFragment_to_walletNameFragment)
        }
    }

    fun onCreateWalletPressed() {
        Log.d(javaClass.name, "OnCreateWalletPressed")
        viewModelScope.launch {
            walletRepos.createWallet(walletName.value, currency.value, BigDecimal(0), limit.value)
            walletCreated.value = true
        }
        navigationDispatcher.emit { navController ->
            navController.navigate(R.id.action_walletCreatingInfoFragment_to_walletListFragment)
        }
    }

}