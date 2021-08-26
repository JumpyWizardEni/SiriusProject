package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletCreatingInfoBinding
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel


class WalletCreatingInfoFragment : Fragment(R.layout.fragment_wallet_creating_info) {

    private val walletCreatingViewModel: WalletCreatingViewModel by activityViewModels()
    private val binding: FragmentWalletCreatingInfoBinding by viewBinding(
        FragmentWalletCreatingInfoBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addWallet.setOnClickListener {
            binding.addWallet.isEnabled = false
            walletCreatingViewModel.onCreateWalletPressed()
        }
        walletCreatingViewModel.loadingState.collectWhenStarted(viewLifecycleOwner, {
            when (it) {
                LoadingState.NoConnection, LoadingState.UnexpectedError -> {
                    binding.addWallet.isEnabled = true
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_LONG).show()
                }
            }

        })
        binding.toolbarLayout.toolbar.title = getString(R.string.new_wallet)
        binding.walletNameLayout.setOnClickListener {
            walletCreatingViewModel.onWalletNamePressed(R.layout.fragment_wallet_creating_info)
        }
        binding.limitLayout.setOnClickListener {
            walletCreatingViewModel.onLimitPressed(R.layout.fragment_wallet_creating_info)
        }

        with(walletCreatingViewModel) {
            walletName.collectWhenStarted(viewLifecycleOwner, {
                binding.walletNameText.text = it
            })

            limit.collectWhenStarted(viewLifecycleOwner, {
                binding.limit.text = it.toPlainString()
            })

            loadingState.collectWhenStarted(viewLifecycleOwner, {
                if (it == LoadingState.UnexpectedError || it == LoadingState.NoConnection) {
                    binding.addWallet.isEnabled = true
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_LONG).show()
                }
            })
        }
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}
