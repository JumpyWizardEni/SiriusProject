package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentOperationChangeBinding
import com.siriusproject.coshelek.utils.DateTimeConverter
import com.siriusproject.coshelek.utils.DateTimeDialog
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel.Companion.TRANSACTION
import com.siriusproject.coshelek.wallet_list.ui.view.LoadingState
import kotlinx.coroutines.flow.filterNotNull

class OperationEditFragment : Fragment(R.layout.fragment_operation_change) {

    companion object {
        const val EDIT_FRAGMENT = 0
    }

    private val binding by viewBinding(FragmentOperationChangeBinding::bind)

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSummary()
        arguments?.let {
            it.getSerializable(TRANSACTION)?.let {
                transactionViewModel.setModel(it as TransactionUiModel)
            }
        }
        binding.toolbarHolder.toolbar.title = getString(R.string.edit_op)
        binding.createOpButton.setOnClickListener {
            binding.createOpButton.isEnabled = false
            transactionViewModel.onEditTransactionPressed()
        }

        binding.sumAmount.setOnClickListener {
            transactionViewModel.onSumPressed(EDIT_FRAGMENT)
        }


        binding.createOpButton.text = getString(R.string.edit_op)
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.dateContainer.setOnClickListener {
            DateTimeDialog().createDialog(requireContext(), transactionViewModel)
        }

        binding.amountContainer.setOnClickListener {
            transactionViewModel.onSumPressed(EDIT_FRAGMENT)
        }

        binding.categoryContainer.setOnClickListener {
            transactionViewModel.onCategoryPressed(EDIT_FRAGMENT)
        }

        binding.typeContainer.setOnClickListener {
            transactionViewModel.onTypePressed(EDIT_FRAGMENT)
        }

        transactionViewModel.loadingState.collectWhenStarted(viewLifecycleOwner, {
            when (it) {
                LoadingState.NoConnection, LoadingState.UnexpectedError -> {
                    binding.createOpButton.isEnabled = true
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_LONG).show()
                }
            }

        })

    }

    private fun setSummary() {
        transactionViewModel.amount.filterNotNull().collectWhenStarted(this) {
            binding.sumAmount.text = it
        }
        transactionViewModel.type.filterNotNull().collectWhenStarted(this) {
            binding.opType.text = when (it) {
                TransactionType.Income -> resources.getString(R.string.income)
                TransactionType.Expense -> resources.getString(R.string.outcome)
                null -> ""
            }
        }
        transactionViewModel.category.filterNotNull().collectWhenStarted(this) {
            binding.category.text = it.name
        }

        transactionViewModel.date.collectWhenStarted(viewLifecycleOwner, {
            val formatter = DateTimeConverter(requireContext())
            binding.opDate.text = getString(
                R.string.date_time,
                formatter.getCurrentDate(it.toLocalDate()),
                formatter.getCurrentTime(it.toLocalTime())
            )
        })
    }
}
