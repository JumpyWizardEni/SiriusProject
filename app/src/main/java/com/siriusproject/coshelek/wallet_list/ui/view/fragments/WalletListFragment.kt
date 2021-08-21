package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletListBinding
import com.siriusproject.coshelek.wallet_list.ui.adapters.WalletListAdapter
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletListFragment : Fragment(R.layout.fragment_wallet_list) {

    private val binding: FragmentWalletListBinding by viewBinding(FragmentWalletListBinding::bind)
    private val walletsViewModel: WalletListViewModel by activityViewModels()
    private val adapter = WalletListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        walletsViewModel.wallets.observe(viewLifecycleOwner, {
            adapter.setItems(it)
            with(binding) {
                with(walletsViewModel) {
                    getMainBalance()?.let {
                        balance.text = it.toString()
                    }
                    getMainExpense()?.let {
                        incomeAmount.text = it.toString()
                    }
                    getMainIncome()?.let {
                        expenseAmount.text = it.toString()
                    }
                }
            }
            showRecordsText(adapter.itemCount == 0)
        })
    }

    private fun showRecordsText(show: Boolean) {
        binding.noWalletsYet.visibility = if (show) View.VISIBLE else View.GONE
    }


}