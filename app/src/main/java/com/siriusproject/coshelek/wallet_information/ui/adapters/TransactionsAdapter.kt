package com.siriusproject.coshelek.wallet_information.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.DateViewHolderBinding
import com.siriusproject.coshelek.databinding.OperationViewHolderBinding
import com.siriusproject.coshelek.utils.DateTimeConverter
import com.siriusproject.coshelek.wallet_information.data.model.TransactionHeaderModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionListItem
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import java.time.LocalDate

class TransactionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //TODO converter injection

    class TransactionViewHolder(
        private val binding: OperationViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TransactionUiModel) {
            with(binding) {
                //TODO icon, пока что заглушка
                val converter = DateTimeConverter(binding.root.context)
                categoryIc.setImageResource(R.drawable.ic_supermarket)
                categoryText.text = model.category.name
                opName.text = model.name
                amount.text = model.amount.toString()
                opTime.text = converter.getCurrentTime(model.date.toLocalTime())
            }
        }
    }

    class DateViewHolder(
        private val binding: DateViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TransactionHeaderModel) {
            with(binding) {
                //TODO конвертирование в Сегодня/Вчера/дата
                val converter = DateTimeConverter(binding.root.context)
                dateText.text = converter.getCurrentDate(model.date)
            }
        }
    }

    private val transactions: MutableList<TransactionListItem> = mutableListOf()
    private var previousDate: LocalDate? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.date_view_holder) {
            val binding =
                DateViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateViewHolder(binding)
        } else {
            val binding =
                OperationViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            TransactionViewHolder(binding)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> holder.bind(transactions[position] as TransactionUiModel)
            is DateViewHolder -> holder.bind(transactions[position] as TransactionHeaderModel)
        }
    }

    override fun getItemCount() = transactions.size

    fun addNewItem(model: TransactionUiModel) {
        if (previousDate != model.date.toLocalDate()) { //Нужен новый разделитель
            transactions.add(TransactionHeaderModel(model.date.toLocalDate()))
            transactions.add(model)
            notifyItemRangeChanged(transactions.size - 2, 2, null)
        } else {
            transactions.add(model)
            notifyItemInserted(transactions.size - 1)
        }
        previousDate = model.date.toLocalDate()
    }


    override fun getItemViewType(position: Int): Int {
        return when (transactions[position]) {
            is TransactionUiModel -> R.layout.operation_view_holder
            is TransactionHeaderModel -> R.layout.date_view_holder

        }
    }

    fun setTransactions(newList: List<TransactionUiModel>?) {
        transactions.clear()
        previousDate = null
        newList?.forEach { model ->
            if (previousDate != model.date.toLocalDate()) { //Нужен новый разделитель
                transactions.add(TransactionHeaderModel(model.date.toLocalDate()))
                transactions.add(model)
//                notifyItemRangeChanged(transactions.size - 2, 2, null)
            } else {
                transactions.add(model)
//                notifyItemInserted(transactions.size - 1)
            }
            previousDate = model.date.toLocalDate()
        }
        notifyItemRangeChanged(0, transactions.size)
    }

}