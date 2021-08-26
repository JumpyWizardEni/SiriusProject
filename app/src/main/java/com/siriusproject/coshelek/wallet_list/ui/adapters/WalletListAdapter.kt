package com.siriusproject.coshelek.wallet_list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.HiddenWalletsHeaderBinding
import com.siriusproject.coshelek.databinding.WalletViewHolderBinding
import com.siriusproject.coshelek.wallet_list.ui.model.WalletListItem
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import com.siriusproject.coshelek.wallet_list.ui.model.WalletVisibilityHeader
import com.siriusproject.coshelek.wallet_list.ui.view.viewholder.WalletViewHolder
import com.siriusproject.coshelek.wallet_list.ui.view.viewholder.WalletVisibilityHolder


class WalletListAdapter(
    private val delete: (Int) -> Unit,
    private val openWalletInfo: (Int, String) -> Unit,
    private val editWallet: (Int) -> Unit,
    private val visibilityChange: (Boolean, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var visibility = false
    private val wallets
        get() = asyncListDiffer.currentList.filter {
            if (it is WalletUiModel) {
                if (visibility) {
                    true
                } else {
                    it.visibility
                }
            } else {
                true
            }
        }
    private val viewBinderHelper = ViewBinderHelper()
    private val asyncListDiffer = AsyncListDiffer(this, WalletDiffCallback())
    var isEmpty: Boolean = false

    override fun getItemCount() = wallets.size

    override fun getItemViewType(position: Int): Int {
        return when (asyncListDiffer.currentList[position]) {
            is WalletUiModel -> R.layout.wallet_view_holder
            is WalletVisibilityHeader -> R.layout.hidden_wallets_header
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.wallet_view_holder -> {
                val binding =
                    WalletViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                val holder = WalletViewHolder(binding)
                holder.binding.delete.setOnClickListener {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        delete.invoke((wallets[holder.bindingAdapterPosition] as WalletUiModel).id)
                        holder.binding.swipeRevealLayout.close(true)
                    }

                }
                holder.binding.viewHolderLayout.setOnClickListener {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        openWalletInfo.invoke(
                            (wallets[holder.bindingAdapterPosition] as WalletUiModel).id,
                            (wallets[holder.bindingAdapterPosition] as WalletUiModel).name
                        )
                        holder.binding.swipeRevealLayout.close(true)
                    }
                }
                holder.binding.edit.setOnClickListener {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        editWallet.invoke((wallets[holder.bindingAdapterPosition] as WalletUiModel).id)
                        holder.binding.swipeRevealLayout.close(true)
                    }
                }

                holder.binding.visibility.setOnClickListener {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        visibilityChange.invoke(
                            !(wallets[holder.bindingAdapterPosition] as WalletUiModel).visibility,
                            (wallets[holder.bindingAdapterPosition] as WalletUiModel).id
                        )
                        holder.binding.swipeRevealLayout.close(true)
                    }

                }
                holder
            }
            else -> {
                val holder = WalletVisibilityHolder(
                    HiddenWalletsHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                holder.setOnClickListener {
                    visibility = !visibility
                }
                holder

            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WalletViewHolder -> {
                viewBinderHelper.bind(
                    holder.swipeRevealLayout,
                    (wallets[position] as WalletUiModel).id.toString()
                )
                holder.bind((wallets[position] as WalletUiModel))
            }
        }

    }

    fun setItems(newList: List<WalletUiModel>?) {
        isEmpty = newList?.size == 0
        asyncListDiffer.submitList(mutableListOf<WalletListItem>().apply {
            var isHidden = false
            newList?.sortedBy {
                !it.visibility
            }?.forEach { model ->
                if (!isHidden && !model.visibility) {
                    add(WalletVisibilityHeader)
                    add(model)
                    isHidden = true
                } else {
                    add(model)
                }
            }
        })
    }
}
