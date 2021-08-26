package com.siriusproject.coshelek.categories_info.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.ui.view.view_models.CategoryAddViewModel
import com.siriusproject.coshelek.databinding.FragmentWalletNameBinding


class CategoryNameFragment : Fragment(R.layout.fragment_wallet_name) {

    private val binding by viewBinding(FragmentWalletNameBinding::bind)
    private val viewModel: CategoryAddViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarHolder.toolbar.title = getString(R.string.set_category_name)
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.enterButton.setOnClickListener {
            viewModel.onNameReadyPressed(binding.sumEditText.text.toString())
        }
        binding.textField.hint = getString(R.string.enter_category_name)
        binding.sumEditText.doOnTextChanged { text, start, before, count ->
            val button = binding.enterButton
            button.isEnabled = !text.isNullOrEmpty()
        }
    }
}