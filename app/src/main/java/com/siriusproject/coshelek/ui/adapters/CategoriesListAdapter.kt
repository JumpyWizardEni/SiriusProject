package com.siriusproject.coshelek

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.data.model.CategoryUiModel
import com.siriusproject.coshelek.databinding.CategoryItemBinding
import com.siriusproject.coshelek.ui.adapters.CategoriesDiffUtil
import com.siriusproject.coshelek.ui.adapters.CategoryViewHolder


class CategoriesListAdapter(val onCategorySelected: (CategoryUiModel)->Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedCat = 0

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) onBindViewHolder(holder, position)
        else {
            (holder as CategoryViewHolder).select(payloads[0] as Boolean)
        }
    }

    private val categories = mutableListOf<CategoryUiModel>()

    fun setData(newCats: MutableList<CategoryUiModel>) {
        val productDiffUtilCallback = CategoriesDiffUtil(categories, newCats)
        val productDiffResult = DiffUtil.calculateDiff(productDiffUtilCallback)
        categories.clear()
        categories.addAll(newCats)
        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CategoryViewHolder(binding)
        binding.root.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION)
                onClicked(categories[viewHolder.adapterPosition], viewHolder.adapterPosition)
        }
        return viewHolder
    }

    private fun onClicked(cat: CategoryUiModel, posInList: Int) {
        notifyItemChanged(selectedCat, false)
        selectedCat = posInList
        notifyItemChanged(posInList, true)
        onCategorySelected(cat)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(categories[position], position==selectedCat)
    }

    override fun getItemCount() = categories.size



}