package com.siriusproject.coshelek.wallet_list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.databinding.WalletViewHolderBinding
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import com.siriusproject.coshelek.wallet_list.ui.view.viewholder.WalletViewHolder

class WalletListAdapter : RecyclerView.Adapter<WalletViewHolder>() {

    private val wallets = mutableListOf<WalletUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding =
            WalletViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(wallets[position])
    }

    override fun getItemCount(): Int {
        return wallets.size
    }

    fun setItems(newList: List<WalletUiModel>) {
        wallets.clear()
        wallets.addAll(newList)

        notifyItemRangeChanged(0, wallets.size)
    }
}