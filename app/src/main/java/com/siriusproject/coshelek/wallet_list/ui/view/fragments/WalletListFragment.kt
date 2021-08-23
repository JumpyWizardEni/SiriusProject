package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletListBinding
import com.siriusproject.coshelek.wallet_information.ui.view.MainScreenActivity
import com.siriusproject.coshelek.wallet_list.ui.adapters.WalletListAdapter
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletListFragment : Fragment(R.layout.fragment_wallet_list) {

    companion object {
        const val WALLET_ID = "wallet_id"
    }

    private val binding: FragmentWalletListBinding by viewBinding(FragmentWalletListBinding::bind)
    private val walletsViewModel: WalletListViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deleteClickLambda: (Int) -> Unit = {
            walletsViewModel.deleteWallet(it)
        }
        val onWalletClickLambda: (Int) -> Unit = {
            startWalletInfoActivity(it)
        }
        val onEditClickLambda: (Int) -> Unit = {
            walletsViewModel.onEditWalletPressed()
        }
        val adapter = WalletListAdapter(deleteClickLambda, onWalletClickLambda)
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
                    binding.balance.text = it.toPlainString()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.income.collect {
                    binding.incomeAmount.text = it.toPlainString()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                walletsViewModel.expense.collect {
                    binding.expenseAmount.text = it.toPlainString()
                }
            }
        }

        binding.addWallet.setOnClickListener {
            Log.d(javaClass.name, "Button pressed")
            walletsViewModel.onCreateWalletPressed()
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