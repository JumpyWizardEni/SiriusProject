package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentEnterSumBinding
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterSumFragment : Fragment(R.layout.fragment_enter_sum) {

    private val binding by viewBinding(FragmentEnterSumBinding::bind)

    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterButton.isEnabled = false

        binding.enterButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_enterSumFragment_to_typeOperationFragment
            )
        }

        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_enterSumFragment_to_walletFragment)
        }

        binding.sumEditText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                binding.enterButton.isEnabled = false
                binding.textField.error = resources.getString(R.string.wrong_amount)
            } else {
                binding.enterButton.isEnabled = true
                binding.textField.error = null
            }
        }

        binding.enterButton.setOnClickListener {
            setAmount()
            findNavController().navigate(R.id.action_enterSumFragment_to_typeOperationFragment)
        }

        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_enterSumFragment_to_walletFragment)
        }
    }

    private fun setAmount() {
        viewModel.pushAmount(binding.sumEditText.text.toString())
    }
}