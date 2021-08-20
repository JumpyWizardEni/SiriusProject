package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentEnterSumBinding
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel

class EnterSumFragment : Fragment() {

    private var _binding: FragmentEnterSumBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterSumBinding.inflate(layoutInflater)

        binding.enterButton.isEnabled = false

        binding.enterButton.setOnClickListener {
            val data = bundleOf("sum" to binding.sumEditText.text.toString())
            findNavController().navigate(
                R.id.action_enterSumFragment_to_typeOperationFragment,
                data
            )
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_enterSumFragment_to_walletFragment)
        }

        binding.sumEditText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                binding.enterButton.isEnabled = false
                binding.textField.error = "Введите корректную сумму"
            } else {
                binding.enterButton.isEnabled = true
                binding.textField.error = null
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}