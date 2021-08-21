package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentOperationChangeBinding
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class OperationChangeFragment : Fragment(R.layout.fragment_operation_change) {

    private val binding by viewBinding(FragmentOperationChangeBinding::bind)

    private val walletViewModel: WalletViewModel by activityViewModels()
    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSummary()

        binding.createOpButton.setOnClickListener {
            walletViewModel.addNewTransaction(
                TransactionUiModel(
                    0,
                    "1",
                    CategoryUiModel(
                        id = 0,
                        name = "Зарплата",
                        type = TransactionType.Income,
                        picture = R.drawable.ic_cat_multivalue_cards,
                        color = Color.GREEN),
                    TransactionType.Income,
                    30,
                    "",
                    LocalDateTime.now()
                )
            )
            findNavController().navigate(R.id.action_operationChangeFragment_to_walletFragment)
        }

        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_operationChangeFragment_to_categorySelectFragment)
        }

    }

    private fun initSummary() {
        with(binding) {
            sumAmount.text = transactionViewModel.amount
            opType.text = when (transactionViewModel.type) {
                TransactionType.Income -> resources.getString(R.string.income)
                TransactionType.Consumption -> resources.getString(R.string.outcome)
                null -> ""
            }
            category.text = transactionViewModel.category?.name
        }
    }
}