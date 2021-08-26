package com.siriusproject.coshelek.wallet_list.ui.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.HiddenWalletsHeaderBinding

class WalletVisibilityHolder(private val binding: HiddenWalletsHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var areWalletsHidden = true

    fun setOnClickListener(visibilityLambda: () -> Unit) {
        binding.hideWalletContainer.setOnClickListener {
            visibilityLambda()
            areWalletsHidden = !areWalletsHidden
            val context = binding.root.context
            binding.hideText.text =
                if (areWalletsHidden) {
                    context.getString(R.string.hidden_wallets)
                } else {
                    context.getString(R.string.hide_wallets)
                }
            val image = if (areWalletsHidden) {
                R.drawable.ic_down_arrow_blue
            } else {
                R.drawable.ic_up_arrow_blue
            }
            binding.arrow.setImageResource(image)
        }
    }
}
