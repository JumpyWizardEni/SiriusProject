package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.FragmentWalletBinding
import com.siriusproject.coshelek.wallet_information.ui.adapters.TransactionsAdapter
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.WalletViewModel

class WalletFragment : Fragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter = TransactionsAdapter()
    private val walletViewModel: WalletViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(layoutInflater)

        binding.addOperation.setOnClickListener {
            findNavController().navigate(R.id.action_walletFragment_to_enterSumFragment)
        }
        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        }
        walletViewModel.transactions.observe(viewLifecycleOwner, {
            recyclerAdapter.setTransactions(it)
            showRecordsText(recyclerAdapter.itemCount == 0)
        })

        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}