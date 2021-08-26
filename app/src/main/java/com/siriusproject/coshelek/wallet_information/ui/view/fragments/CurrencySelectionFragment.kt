package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentCurrencySelectionBinding
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT

class CurrencySelectionFragment() : Fragment(R.layout.fragment_currency_selection) {

    private val binding by viewBinding(FragmentCurrencySelectionBinding::bind)
    private val transactionViewModel: TransactionViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            var text = ""
            var value = ""
            when (checkedId) {
                R.id.radio_button_rub -> {
                    text = binding.radioButtonRub.text.toString()
                    value = "RUB"
                }
                R.id.radio_button_usd -> {
                    text = binding.radioButtonUsd.text.toString()
                    value = "USD"
                }
                R.id.radio_button_eur -> {
                    text = binding.radioButtonEur.text.toString()
                    value = "EUR"
                }
            }
            transactionViewModel.setCurrency(value, text)
        }

        binding.enterButton.setOnClickListener {
            transactionViewModel.onCurrencyReadyPressed(requireArguments().getInt(PREVIOUS_FRAGMENT))
        }

    }
}