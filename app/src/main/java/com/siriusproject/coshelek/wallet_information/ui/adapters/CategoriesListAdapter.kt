package com.siriusproject.coshelek.wallet_information.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.databinding.CategoryItemBinding
import com.siriusproject.coshelek.utils.CategoriesDiffUtil
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.ui.viewholders.CategoryViewHolder

class CategoriesListAdapter(val onCategorySelected: (CategoryUiModel?, Boolean) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categories = mutableListOf<CategoryUiModel>()
    private var selectedCat = UNSELECTED
    companion object {
        private const val UNSELECTED = -1
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) onBindViewHolder(holder, position)
        else {
            (holder as CategoryViewHolder).select(payloads[0] as Boolean)
        }
    }

    fun setData(newCats: List<CategoryUiModel>) {
        val productDiffUtilCallback = CategoriesDiffUtil(categories, newCats)
        val productDiffResult = DiffUtil.calculateDiff(productDiffUtilCallback)
        categories.clear()
        categories.addAll(newCats)
        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CategoryViewHolder(binding)
        binding.root.setOnClickListener {
            if (viewHolder.bindingAdapterPosition != RecyclerView.NO_POSITION)
                onClicked(categories[viewHolder.bindingAdapterPosition],)
        }
        return viewHolder
    }

    private fun onClicked(newSelectedCat: CategoryUiModel) {
        val newSelectedPos = categories.indexOf(newSelectedCat)
        when(selectedCat){
            newSelectedPos -> {
                notifyItemChanged(selectedCat, false)
                selectedCat = UNSELECTED
                onCategorySelected(null, false)
            }
            else -> {
                if(selectedCat != UNSELECTED)
                    notifyItemChanged(selectedCat, false)
                selectedCat = newSelectedPos
                notifyItemChanged(selectedCat, true)
                onCategorySelected(newSelectedCat, true)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(categories[position], position == selectedCat)
    }

    override fun getItemCount() = categories.size


}