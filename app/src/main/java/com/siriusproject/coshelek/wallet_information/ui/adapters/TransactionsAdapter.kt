package com.siriusproject.coshelek.wallet_information.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.DateViewHolderBinding
import com.siriusproject.coshelek.databinding.OperationViewHolderBinding
import com.siriusproject.coshelek.databinding.ProgressViewHolderBinding
import com.siriusproject.coshelek.utils.CurrencyFormatter
import com.siriusproject.coshelek.utils.DateTimeConverter
import com.siriusproject.coshelek.wallet_information.data.model.TransactionHeaderModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionListItem
import com.siriusproject.coshelek.wallet_information.data.model.TransactionProgressBarModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import java.time.LocalDate

class TransactionsAdapter(
    private val delete: (Int) -> Unit,
    private val edit: (TransactionUiModel) -> Unit,
    private val loadNextPage: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isEmpty: Boolean = false
    private val asyncListDiffer = AsyncListDiffer(this, TransactionsDiffCallback())

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (asyncListDiffer.currentList[position]) {
            is TransactionUiModel -> R.layout.operation_view_holder
            is TransactionHeaderModel -> R.layout.date_view_holder
            is TransactionProgressBarModel -> R.layout.progress_view_holder
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.date_view_holder -> {
                val binding =
                    DateViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                DateViewHolder(binding)
            }
            R.layout.operation_view_holder -> {
                val binding =
                    OperationViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                val holder = TransactionViewHolder(binding)
                holder.binding.delete.setOnClickListener {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION)

                        delete.invoke((asyncListDiffer.currentList[holder.bindingAdapterPosition] as TransactionUiModel).id)
                }
                holder.binding.edit.setOnClickListener {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION)
                        edit.invoke(
                            asyncListDiffer.currentList[holder.bindingAdapterPosition] as TransactionUiModel

                        )
                }
                holder
            }
            else -> {
                ProgressViewHolder(
                    ProgressViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> holder.bind(asyncListDiffer.currentList[position] as TransactionUiModel)
            is DateViewHolder -> holder.bind(asyncListDiffer.currentList[position] as TransactionHeaderModel)
            is ProgressViewHolder -> holder.bind(loadNextPage)
        }
    }

    fun setTransactions(newList: List<TransactionUiModel>?, isAllLoaded: Boolean) {
        isEmpty = newList?.size == 0
        asyncListDiffer.submitList(mutableListOf<TransactionListItem>().apply {
            var previousDate: LocalDate? = null
            val sortedList: MutableList<TransactionUiModel>? = newList?.sortedBy {
                it.date
            }?.toMutableList()
            sortedList?.reverse()
            sortedList?.forEach { model ->
                if (previousDate != model.date.toLocalDate()) { //Нужен новый разделитель
                    add(TransactionHeaderModel(model.date.toLocalDate()))
                    add(model)
                } else {
                    add(model)
                }
                previousDate = model.date.toLocalDate()
            }
            if (!isAllLoaded) {
                add(TransactionProgressBarModel)
            }
        })

    }

    class TransactionViewHolder(
        val binding: OperationViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val converter = DateTimeConverter(binding.root.context)
        private val formatter = CurrencyFormatter()
        fun bind(model: TransactionUiModel) {
            with(binding) {
                swipeRevealLayout.close(false)
                categoryIc.categoryIcon.setImageResource(model.category.picture)
                categoryIc.categoryIcon.backgroundTintList =
                    ColorStateList.valueOf(model.category.color)
                categoryText.text = model.category.name
                opName.text = model.type.toString()
                amount.text = formatter.formatBigDecimalWithSign(model.amount, model.type)
                opTime.text = converter.getCurrentTime(model.date.toLocalTime())
            }
        }
    }

    class DateViewHolder(
        private val binding: DateViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TransactionHeaderModel) {
            with(binding) {
                val converter = DateTimeConverter(binding.root.context)
                dateText.text = converter.getCurrentDate(model.date)
            }
        }
    }

    class ProgressViewHolder(
        binding: ProgressViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadNextPage: () -> Unit) {
            loadNextPage.invoke()
        }
    }

}