package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentOperationChangeBinding
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class OperationChangeFragment : Fragment() {

    private var _binding: FragmentOperationChangeBinding? = null
    private val binding get() = _binding!!
    private val walletViewModel: WalletViewModel by activityViewModels()
    private val transactionViewModel: TransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOperationChangeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewModel.transactionModel?.let { setUiData(it) }

        val amount = arguments?.getString("sum")
        binding.sumAmount.text = amount.toString()
        binding.createOpButton.setOnClickListener {
            walletViewModel.addNewTransaction(
                TransactionUiModel(
                    0,
                    "1",
                    CategoryUiModel(
                        id=0,
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
            findNavController().navigate(R.id.action_operationChangeFragment_to_typeOperationFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUiData(model: TransactionUiModel) {
        with(binding) {
            sumAmount.text = model.amount.toString()
            opType.text = when (model.type) {
                TransactionType.Income -> resources.getString(R.string.income)
                TransactionType.Expence -> resources.getString(R.string.outcome)
            }
            category.text = model.category.name
            opDate.text = model.date.toString()
        }
    }
}