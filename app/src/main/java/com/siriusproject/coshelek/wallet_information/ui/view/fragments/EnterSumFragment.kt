package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
        binding.toolbarHolder.toolbar.title = getString(R.string.enter_sum)
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
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
            viewModel.onSumReadyPressed()
        }

    }

    private fun setAmount() {
        viewModel.pushAmount(binding.sumEditText.text.toString())
    }
}