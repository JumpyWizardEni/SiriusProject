package com.siriusproject.coshelek.wallet_list.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.ActivityWalletListBinding
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WalletListActivity : AppCompatActivity(R.layout.activity_wallet_list) {
    private val walletsViewModel: WalletListViewModel by viewModels()
    private val walletsCreatingViewModel: WalletCreatingViewModel by viewModels()
    private val binding: ActivityWalletListBinding by viewBinding(ActivityWalletListBinding::bind)

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.wallets_list_container) as NavHostFragment
        navController = navHostFragment.navController

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigationDispatcher.navigationEmitter.collect {
                    Log.d(javaClass.name, "Nav Command invoking...")
                    it?.invoke(navController)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsCreatingViewModel.walletCreated.collect { walletCreated ->
                    if (walletCreated) {
                        walletsViewModel.getWallets()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        navigationDispatcher.navigationEmitter.value = null
    }

    override fun onBackPressed() {
        Log.d(javaClass.name, "BackPressed")
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }
}
