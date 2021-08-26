package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentCurrencySelectionBinding

class CurrencySelectionFragment() : Fragment(R.layout.fragment_currency_selection) {

    private val binding by viewBinding(FragmentCurrencySelectionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_rub -> {
                }
                R.id.radio_button_usd -> {
                }
                R.id.radio_button_eur -> {
                }
            }
        }

    }
}