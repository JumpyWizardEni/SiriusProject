package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentOperationChangeBinding
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class OperationChangeFragment : Fragment(R.layout.fragment_operation_change) {

    private val binding by viewBinding(FragmentOperationChangeBinding::bind)

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSummary()

        binding.toolbarHolder.toolbar.title = getString(R.string.create_op)
        binding.createOpButton.setOnClickListener {
            transactionViewModel.onCreateTransactionButton()
        }

        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun setSummary() {
        transactionViewModel.amount.filterNotNull().collectWhenStarted(this) {
            binding.sumAmount.text = it
        }
        transactionViewModel.type.filterNotNull().collectWhenStarted(this) {
            binding.opType.text = when (it) {
                TransactionType.Income -> resources.getString(R.string.income)
                TransactionType.Expence -> resources.getString(R.string.outcome)
                null -> ""
            }
        }
        transactionViewModel.category.filterNotNull().collectWhenStarted(this) {
            binding.category.text = it.name
        }
    }
}