package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletBinding
import com.siriusproject.coshelek.utils.CurrencyFormatter
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.ui.adapters.TransactionsAdapter
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_ID
import com.siriusproject.coshelek.wallet_list.ui.view.fragments.WalletListFragment.Companion.WALLET_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@AndroidEntryPoint
class WalletFragment : Fragment(R.layout.fragment_wallet) {

    @Inject
    lateinit var currencyFormatter: CurrencyFormatter
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

        val onEditClickLambda: (TransactionUiModel) -> Unit = {
            walletViewModel.onEditWalletPressed(it)
        }
        val loadPageLambda: () -> Unit = {
            walletViewModel.loadNewPage()
        }
        recyclerAdapter = TransactionsAdapter(deleteClickLambda, onEditClickLambda, loadPageLambda)

        val walletId = requireActivity().intent!!.getIntExtra(WALLET_ID, 0)
        walletViewModel.walletId = walletId
        binding.walletLabel.text = requireActivity().intent?.getStringExtra(WALLET_NAME)
        binding.addOperation.setOnClickListener {
            walletViewModel.onAddOperationClicked(walletId)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            walletViewModel.fetchTransactions()
            walletViewModel.getWalletInfo(walletId)
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
        walletViewModel.transactions.combine(
            walletViewModel.isAllLoaded
        ) { transactions, isAllLoaded ->
            Pair(transactions, isAllLoaded)
        }.collectWhenStarted(viewLifecycleOwner, { (transactions, isAllLoaded) ->
            if (!walletViewModel.firstCollect) {
                recyclerAdapter.setTransactions(transactions, isAllLoaded)
            } else {
                walletViewModel.firstCollect = false
            }
            showRecordsText(recyclerAdapter.isEmpty)

        })
        walletViewModel.balance.combine(
            walletViewModel.currency
        ) { balance, currency ->
            Pair(balance, currency)
        }.collectWhenStarted(viewLifecycleOwner, { (balance, currency) ->
            binding.currentVolume.text =
                currencyFormatter.formatBigDecimal(balance, currency ?: "RUB")
        })
        walletViewModel.income.combine(
            walletViewModel.currency
        ) { income, currency ->
            Pair(income, currency)
        }.collectWhenStarted(viewLifecycleOwner, { (income, currency) ->
            binding.income.text = currencyFormatter.formatBigDecimal(income, currency ?: "RUB")
        })
        walletViewModel.expence.combine(
            walletViewModel.currency
        ) { expence, currency ->
            Pair(expence, currency)
        }.collectWhenStarted(viewLifecycleOwner, { (expence, currency) ->
            binding.expence.text = currencyFormatter.formatBigDecimal(expence, currency ?: "RUB")
        })

        walletViewModel.limitReached.collectWhenStarted(viewLifecycleOwner, {
            binding.exceededLimitText.isVisible = it
        })
        walletViewModel.fetchTransactions()
        walletViewModel.getWalletInfo(walletId)
        walletViewModel.loadingState.collectWhenStarted(viewLifecycleOwner, {
            when (it) {
                LoadingState.Loading -> {
                    with(binding) {
                        noRecordsYet.visibility = View.GONE
                        exceededLimitText.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = true
                        recyclerView.visibility = View.GONE
                        noInternetHeader.noInternet.visibility = View.GONE
                    }
                }
                LoadingState.NoConnection -> {
                    with(binding) {
                        exceededLimitText.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = false
                        noRecordsYet.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        noInternetHeader.noInternet.visibility = View.VISIBLE
                    }
                }
                LoadingState.UnexpectedError -> {
                    with(binding) {
                        swipeRefreshLayout.isRefreshing = false
                        exceededLimitText.visibility = View.GONE
                        noRecordsYet.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        noInternetHeader.noInternet.visibility = View.VISIBLE
                    }

                }
                LoadingState.Ready -> {
                    with(binding) {
                        swipeRefreshLayout.isRefreshing = false
                        noRecordsYet.isVisible = recyclerAdapter.isEmpty
                        recyclerView.visibility = View.VISIBLE
                        noInternetHeader.noInternet.visibility = View.GONE
                    }
                }
            }
        })

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