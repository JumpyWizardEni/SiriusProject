package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletNameBinding
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT


class WalletNameFragment : Fragment(com.siriusproject.coshelek.R.layout.fragment_wallet_name) {

    private val binding by viewBinding(FragmentWalletNameBinding::bind)
    private val walletCreatingViewModel: WalletCreatingViewModel by activityViewModels()

    companion object {
        const val MAX_WALLET_NAME_LENGTH = 30
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.toolbarHolder.toolbar.title =
            getString(com.siriusproject.coshelek.R.string.set_wallet_name)
        binding.sumEditText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty() || text.contains("\n") || text.isBlank() || text.length > MAX_WALLET_NAME_LENGTH) {
                binding.enterButton.isEnabled = false
                binding.textField.error = resources.getString(R.string.wrong_wallet_name)
            } else {
                binding.enterButton.isEnabled = true
                binding.textField.error = null
            }
        }
        binding.enterButton.setOnClickListener {
            Log.d(javaClass.name, "EnterButton pressed")
            walletCreatingViewModel.onNameReadyPressed(
                binding.sumEditText.text.toString(),
                requireArguments().getInt(PREVIOUS_FRAGMENT)
            )
        }
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}
