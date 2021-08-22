package com.siriusproject.coshelek.wallet_list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.siriusproject.coshelek.databinding.WalletViewHolderBinding
import com.siriusproject.coshelek.utils.WalletsDiffUtil
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import com.siriusproject.coshelek.wallet_list.ui.view.viewholder.WalletViewHolder


class WalletListAdapter(
    private val delete: (Int) -> Unit,
    private val openWalletInfo: (Int) -> Unit
) :
    RecyclerView.Adapter<WalletViewHolder>() {

    private val wallets = mutableListOf<WalletUiModel>()
    private val viewBinderHelper = ViewBinderHelper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding =
            WalletViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = WalletViewHolder(binding)
        holder.binding.delete.setOnClickListener {
            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION)
                delete.invoke(wallets[holder.bindingAdapterPosition].id)
        }
        holder.binding.viewHolderLayout.setOnClickListener {
            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION)
                openWalletInfo.invoke(wallets[holder.bindingAdapterPosition].id)
        }
        return holder
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        viewBinderHelper.bind(holder.swipeRevealLayout, wallets[position].id.toString())
        holder.bind(wallets[position])
    }

    override fun getItemCount(): Int {
        return wallets.size
    }

    fun setItems(newList: List<WalletUiModel>) {
        val walletDiffUtilCallback = WalletsDiffUtil(wallets, newList)
        val walletDiffResult = DiffUtil.calculateDiff(walletDiffUtilCallback)
        wallets.clear()
        wallets.addAll(newList)
        walletDiffResult.dispatchUpdatesTo(this)
    }
}