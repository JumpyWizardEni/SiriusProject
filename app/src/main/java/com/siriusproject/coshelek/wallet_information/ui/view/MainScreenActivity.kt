package com.siriusproject.coshelek.wallet_information.ui.view

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenActivity : AppCompatActivity(R.layout.activity_main) {
    private val walletViewModel: WalletViewModel by viewModels()
    private val lastTransactionViewMode: TransactionViewModel by viewModels()
}