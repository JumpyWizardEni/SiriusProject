package com.siriusproject.coshelek.wallet_information.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.siriusproject.coshelek.wallet_information.data.model.TransactionHeaderModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionListItem
import com.siriusproject.coshelek.wallet_information.data.model.TransactionProgressBarModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

class TransactionsDiffCallback : DiffUtil.ItemCallback<TransactionListItem>() {
    override fun areItemsTheSame(
        oldItem: TransactionListItem,
        newItem: TransactionListItem
    ): Boolean {
        return if (oldItem is TransactionUiModel && newItem is TransactionUiModel) {
            oldItem.id == newItem.id
        } else if (oldItem is TransactionHeaderModel && newItem is TransactionHeaderModel) {
            oldItem.date == newItem.date
        } else {
            oldItem is TransactionProgressBarModel && newItem is TransactionProgressBarModel
        }
    }

    override fun areContentsTheSame(
        oldItem: TransactionListItem,
        newItem: TransactionListItem
    ): Boolean {
        return oldItem == newItem
    }
}