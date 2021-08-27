package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentEnterSumBinding
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EnterSumFragment : Fragment(R.layout.fragment_enter_sum) {

    private val binding by viewBinding(FragmentEnterSumBinding::bind)

    private val viewModel: TransactionViewModel by activityViewModels()


    companion object {
        const val MAX_TRANSACTION_LENGTH = 10
    }

    override fun onStart() {
        super.onStart()
        arguments?.getInt(WALLET_ID)?.let {
            if (requireArguments().getInt(PREVIOUS_FRAGMENT) == R.layout.fragment_wallet) {
                viewModel.walletId = it

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.enterButton.isEnabled = false
        binding.toolbarHolder.toolbar.title = getString(R.string.enter_sum)
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.sumEditText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty() || !text.matches("^\\d{1,$MAX_TRANSACTION_LENGTH}(\\.\\d{1,2})?\$".toRegex())) {
                binding.enterButton.isEnabled = false
                binding.textField.error = resources.getString(R.string.wrong_amount)
            } else {
                binding.enterButton.isEnabled = true
                binding.textField.error = null
            }
        }

        binding.enterButton.setOnClickListener {
            setAmount()
            viewModel.onSumReadyPressed(
                requireArguments().getInt(PREVIOUS_FRAGMENT),
                R.layout.fragment_enter_sum
            )
        }

        binding.toolbarHolder.toolbar.inflateMenu(R.menu.qr_code_menu)
        binding.toolbarHolder.toolbar.menu.findItem(R.id.qr_code).setOnMenuItemClickListener {
            IntentIntegrator.forSupportFragment(this).initiateScan();
            true
        }
        viewModel.errorFlow.collectWhenStarted(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setAmount() {
        viewModel.pushAmount(binding.sumEditText.text.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            viewModel.onQrReady(result.contents)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
