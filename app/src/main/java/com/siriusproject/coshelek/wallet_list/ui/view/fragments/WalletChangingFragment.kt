package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletChangingBinding
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel

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
            binding.editWallet.isEnabled = false
            walletCreatingViewModel.onEditWalletPressed()
        }
        binding.limitLayout.setOnClickListener {
            walletCreatingViewModel.onLimitPressed(R.layout.fragment_wallet_changing)
        }

        with(walletCreatingViewModel) {
            walletName.collectWhenStarted(viewLifecycleOwner, {
                binding.walletNameText.text = it
            })

            currency.collectWhenStarted(viewLifecycleOwner, {
                binding.currencyValue.text = it
            })

            limit.collectWhenStarted(viewLifecycleOwner, {
                binding.limit.text = it.toPlainString()
            })

            loadingState.collectWhenStarted(viewLifecycleOwner, {
                if (it == LoadingState.UnexpectedError || it == LoadingState.NoConnection) {
                    binding.editWallet.isEnabled = true
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_LONG).show()
                }
            })
        }

    }


}