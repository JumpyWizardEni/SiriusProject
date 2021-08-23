package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletNameBinding
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletCreatingViewModel


class WalletNameFragment : Fragment(R.layout.fragment_wallet_name) {

    private val binding by viewBinding(FragmentWalletNameBinding::bind)
    private val walletCreatingViewModel: WalletCreatingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarHolder.toolbar.title = getString(R.string.set_wallet_name)
        binding.enterButton.setOnClickListener {
            Log.d(javaClass.name, "EnterButton pressed")
            walletCreatingViewModel.onNameReadyPressed(binding.sumEditText.text.toString())
        }
        binding.sumEditText.doOnTextChanged { text, start, before, count ->
            val button = binding.enterButton
            button.isEnabled = !text.isNullOrEmpty()
        }
    }
}