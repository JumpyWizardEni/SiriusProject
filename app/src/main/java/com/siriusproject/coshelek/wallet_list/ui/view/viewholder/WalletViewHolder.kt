package com.siriusproject.coshelek.wallet_list.ui.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.databinding.WalletViewHolderBinding
import com.siriusproject.coshelek.utils.CurrencyFormatter
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel

class WalletViewHolder(val binding: WalletViewHolderBinding) :

    RecyclerView.ViewHolder(binding.root) {
    val swipeRevealLayout = binding.swipeRevealLayout
    val currencyFormatter = CurrencyFormatter()
    fun bind(model: WalletUiModel) {
        with(binding) {
            walletName.text = model.name
            walletBalance.text = currencyFormatter.formatBigDecimal(model.balance)
        }
    }

}