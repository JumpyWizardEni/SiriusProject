package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletChangingBinding
import com.siriusproject.coshelek.wallet_list.ui.view.LoadingState
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WalletChangingFragment : Fragment(R.layout.fragment_wallet_changing) {


    private val binding by viewBinding(FragmentWalletChangingBinding::bind)
    private val walletCreatingViewModel: WalletCreatingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            walletCreatingViewModel.getWalletInfo(requireArguments().getInt(WALLET_ID))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbar.title = getString(R.string.edit_wallet)

        binding.walletNameLayout.setOnClickListener {
            walletCreatingViewModel.onWalletNamePressed(R.layout.fragment_wallet_changing)
        }
        binding.editWallet.setOnClickListener {
            walletCreatingViewModel.onEditWalletPressed()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletCreatingViewModel.walletName.collect {
                    binding.walletNameText.text = it
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletCreatingViewModel.currency.collect {
                    binding.currencyValue.text = it
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletCreatingViewModel.limit.collect {
                    binding.limit.text = it.toPlainString()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletCreatingViewModel.loadingState.collect {
                    if (it == LoadingState.UnexpectedError) {
                        Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


}