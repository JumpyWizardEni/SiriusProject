package com.siriusproject.coshelek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.data.model.CategoryUiModel
import com.siriusproject.coshelek.databinding.CategoryItemBinding
import com.siriusproject.coshelek.databinding.OperationViewHolderBinding

class CategoriesListAdapter(val onCategorySelected: (CategoryUiModel)->Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedCat = 0

    class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryUiModel, selected: Boolean) {
            with(binding){
                catTitle.text = item.name
                catImg.setImageDrawable(item.picture)
                select(selected)
            }
        }
        fun select(selected: Boolean){
            binding.selectedCatIcon.visibility = if(selected) View.VISIBLE else View.GONE
        }
    }

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
        categories.clear()
        categories.addAll(newCats)
        notifyDataSetChanged()
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