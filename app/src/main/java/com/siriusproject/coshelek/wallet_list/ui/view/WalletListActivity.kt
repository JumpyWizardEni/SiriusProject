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
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class WalletListActivity : AppCompatActivity(R.layout.activity_wallet_list) {
    private val walletsViewModel: WalletListViewModel by viewModels()

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
                navigationDispatcher.navigationCommands.collect {
                    Log.d(javaClass.name, "Nav Command invoking...")
                    it.invoke(navController)
                }
            }
        }

        Log.e(javaClass.name, LocalDateTime.now().toString())
    }


    override fun onBackPressed() {
        Log.d(javaClass.name, "BackPressed")
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }
}
