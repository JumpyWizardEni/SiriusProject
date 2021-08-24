package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletBinding
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.ui.adapters.TransactionsAdapter
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment(R.layout.fragment_wallet) {

    private val binding by viewBinding(FragmentWalletBinding::bind)

    private lateinit var recyclerAdapter: TransactionsAdapter

    private val walletViewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deleteClickLambda: (Int) -> Unit = {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
                .setMessage(resources.getString(R.string.delete_transaction_message))
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                    walletViewModel.deleteTransaction(it)
                }
                .show()
        }

        val onEditClickLambda: (Int) -> Unit = {
            walletViewModel.onEditWalletPressed(it)
        }
        recyclerAdapter = TransactionsAdapter(deleteClickLambda, onEditClickLambda)

        val walletId = requireActivity().intent!!.getIntExtra(WALLET_ID, 0)
        walletViewModel.walletId = walletId
        binding.walletLabel.text = requireActivity().intent?.getStringExtra(WALLET_NAME)
        binding.addOperation.setOnClickListener {
            walletViewModel.onAddOperationClicked(walletId)
        }
        binding.toolbarHolder.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        }
        walletViewModel.transactions.collectWhenStarted(viewLifecycleOwner, {
            recyclerAdapter.setTransactions(it)
            showRecordsText(recyclerAdapter.itemCount == 0)
        })
        walletViewModel.fetchTransactions()
    }

    private fun showRecordsText(isRecyclerEmpty: Boolean) {
        if (isRecyclerEmpty) {
            binding.noRecordsYet.visibility = View.VISIBLE
        } else {
            binding.noRecordsYet.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_screen_menu, menu)
    }
}