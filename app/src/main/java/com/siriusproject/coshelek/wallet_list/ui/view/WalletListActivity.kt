package com.siriusproject.coshelek.wallet_list.ui.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.ActivityWalletListBinding
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletListActivity : AppCompatActivity(R.layout.activity_wallet_list) {
    private val walletsViewModel: WalletListViewModel by viewModels()
    private val binding: ActivityWalletListBinding by viewBinding(ActivityWalletListBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        supportFragmentManager.commit {
            replace<WalletListFragment>(R.id.wallets_list_container)
        }
    }
}