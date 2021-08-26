package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletLimitBinding
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletLimitFragment : Fragment(R.layout.fragment_wallet_limit) {

    private val binding by viewBinding(FragmentWalletLimitBinding::bind)

    private val viewModel: WalletCreatingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterButton.isEnabled = false
        binding.toolbarHolder.toolbar.title = getString(R.string.enter_limit)
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.limitEditText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                binding.enterButton.isEnabled = false
                binding.textField.error = resources.getString(R.string.wrong_amount)
            } else {
                binding.enterButton.isEnabled = true
                binding.textField.error = null
            }
        }

        binding.enterButton.setOnClickListener {
            viewModel.onLimitReadyPressed(
                binding.limitEditText.text.toString().toBigDecimal(),
                requireArguments().getInt(PREVIOUS_FRAGMENT)
            )
        }

    }
}