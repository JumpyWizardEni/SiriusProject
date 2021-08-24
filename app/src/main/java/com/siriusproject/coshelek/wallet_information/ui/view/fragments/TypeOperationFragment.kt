package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentTypeOperationBinding
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeOperationFragment : Fragment(R.layout.fragment_type_operation) {

    private val binding by viewBinding(FragmentTypeOperationBinding::bind)

    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterButton.isEnabled = false

        binding.incomeLayout.setOnClickListener {
            binding.imageCheckIncome.isVisible = !binding.imageCheckIncome.isVisible
            binding.imageCheckConsumption.isVisible = false
            setStateButton()
        }

        binding.consumptionLayout.setOnClickListener {
            binding.imageCheckConsumption.isVisible = !binding.imageCheckConsumption.isVisible
            binding.imageCheckIncome.isVisible = false
            setStateButton()
        }
        binding.toolbarHolder.toolbar.title = getString(R.string.pick_type)
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.enterButton.setOnClickListener {
            setCategoryType()
            viewModel.onTypeReadyPressed()
        }
    }

    private fun setStateButton() {
        binding.enterButton.isEnabled =
            !(!binding.imageCheckIncome.isVisible && !binding.imageCheckConsumption.isVisible)
    }

    private fun setCategoryType() {
        viewModel.pushType(
            when (binding.imageCheckIncome.isVisible) {
                true -> TransactionType.Income
                false -> TransactionType.Expence
            }
        )
    }
}