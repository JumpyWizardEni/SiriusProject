package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentTypeOperationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeOperationFragment : Fragment() {

    private var _binding: FragmentTypeOperationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeOperationBinding.inflate(layoutInflater)

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

        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_typeOperationFragment_to_enterSumFragment)
        }

        binding.enterButton.setOnClickListener {
            findNavController().navigate(R.id.action_typeOperationFragment_to_operationChangeFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setStateButton() {
        binding.enterButton.isEnabled =
            !(!binding.imageCheckIncome.isVisible && !binding.imageCheckConsumption.isVisible)
    }
}