package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletListBinding
import com.siriusproject.coshelek.utils.CurrencyFormatter
import com.siriusproject.coshelek.wallet_information.ui.view.MainScreenActivity
import com.siriusproject.coshelek.wallet_list.ui.adapters.WalletListAdapter
import com.siriusproject.coshelek.wallet_list.ui.view.LoadingState
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WalletListFragment : Fragment(R.layout.fragment_wallet_list) {

    companion object {
        const val WALLET_ID = "wallet_id"
    }

    private val binding: FragmentWalletListBinding by viewBinding(FragmentWalletListBinding::bind)
    private val walletsViewModel: WalletListViewModel by viewModels()
    private lateinit var adapter: WalletListAdapter

    @Inject
    lateinit var formatter: CurrencyFormatter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deleteClickLambda: (Int) -> Unit = {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
                .setMessage(resources.getString(R.string.delete_message))
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                    walletsViewModel.deleteWallet(it)
                }
                .show()
        }
        val onWalletClickLambda: (Int) -> Unit = {
            startWalletInfoActivity(it)
        }
        val onEditClickLambda: (Int) -> Unit = {
            walletsViewModel.onEditWalletPressed(it)
        }
        adapter = WalletListAdapter(deleteClickLambda, onWalletClickLambda, onEditClickLambda)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        Log.d(javaClass.name, "Fragment created")

        recycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.wallets.collect { it ->
                    adapter.setItems(it)
                    showRecordsText(adapter.itemCount == 0)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.mainBalance.collect {
                    binding.balance.text = formatter.formatBigDecimal(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.income.collect {
                    binding.incomeAmount.text = formatter.formatBigDecimal(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.expense.collect {
                    binding.expenseAmount.text = formatter.formatBigDecimal(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.loadingState.collect {
                    showState(it)
                }
            }
        }

        binding.addWallet.setOnClickListener {
            Log.d(javaClass.name, "Button pressed")
            walletsViewModel.onCreateWalletPressed()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            walletsViewModel.fetchWallets()
        }

        walletsViewModel.fetchWallets()

    }

    private fun showState(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.Loading -> {
                with(binding) {
                    noWalletsYet.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true
                    recyclerView.visibility = View.GONE
                    noInternet.visibility = View.GONE
                }
            }
            LoadingState.NoConnection -> {
                with(binding) {
                    swipeRefreshLayout.isRefreshing = false
                    noWalletsYet.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    noInternet.visibility = View.VISIBLE
                }
            }
            LoadingState.UnexpectedError -> {
                with(binding) {
                    swipeRefreshLayout.isRefreshing = false
                    noWalletsYet.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    noInternet.visibility = View.VISIBLE
                }

            }
            LoadingState.Ready -> {
                with(binding) {
                    swipeRefreshLayout.isRefreshing = false
                    noWalletsYet.isVisible = adapter.itemCount == 0
                    recyclerView.visibility = View.VISIBLE
                    noInternet.visibility = View.GONE
                }
            }
        }
    }

    private fun startWalletInfoActivity(walletId: Int) {
        val intent = Intent(activity, MainScreenActivity::class.java)
        intent.putExtra(WALLET_ID, walletId)
        startActivity(intent)
    }

    private fun showRecordsText(show: Boolean) {
        binding.noWalletsYet.visibility = if (show) View.VISIBLE else View.GONE
    }


}