package com.siriusproject.coshelek.utils

import androidx.recyclerview.widget.DiffUtil
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel

class WalletsDiffUtil(
    private val oldList: List<WalletUiModel>,
    private val newList: List<WalletUiModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}