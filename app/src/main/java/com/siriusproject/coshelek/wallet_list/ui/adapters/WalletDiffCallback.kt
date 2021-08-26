package com.siriusproject.coshelek.wallet_list.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.siriusproject.coshelek.wallet_list.ui.model.WalletListItem
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel

class WalletDiffCallback : DiffUtil.ItemCallback<WalletListItem>() {
    override fun areItemsTheSame(
        oldItem: WalletListItem,
        newItem: WalletListItem
    ): Boolean {
        return if (oldItem is WalletUiModel && newItem is WalletUiModel) {
            oldItem.id == newItem.id
        } else true
    }

    override fun areContentsTheSame(
        oldItem: WalletListItem,
        newItem: WalletListItem
    ): Boolean {
        return oldItem == newItem
    }
}