package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentOperationChangeBinding
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.ui.view.TransactionAddingActivity
import com.siriusproject.coshelek.wallet_information.ui.view.TransactionViewModel

class OperationChangeFragment : Fragment() {

    private var _binding: FragmentOperationChangeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOperationChangeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactionModel?.let { setUiData(it) }
        binding.createOpButton.setOnClickListener {
            if (activity != null) {
                (activity as TransactionAddingActivity).finishWithResult()
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
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