package com.siriusproject.coshelek.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.data.model.TransactionUiModel
import com.siriusproject.coshelek.databinding.OperationViewHolderBinding

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {
    class TransactionViewHolder(
        private val binding: OperationViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TransactionUiModel) {
            with(binding) {

                //TODO icon
                categoryText.text = model.category.name
                opName.text = model.name
                amount.text = model.amount.toString()
                opTime.text = model.date.toString()
            }
        }
    }

    private val transactions: MutableList<TransactionUiModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = OperationViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size

    fun setData() {

    }

}