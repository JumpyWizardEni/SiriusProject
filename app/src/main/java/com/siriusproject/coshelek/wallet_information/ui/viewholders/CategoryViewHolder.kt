package com.siriusproject.coshelek.wallet_information.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.databinding.CategoryItemBinding
import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel

class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CategoryUiModel, selected: Boolean) {
        with(binding){
            catTitle.text = item.name
            catImg.setImageResource(item.picture)
            select(selected)
        }
    }
    fun select(selected: Boolean){
        binding.selectedCatIcon.visibility = if(selected) View.VISIBLE else View.GONE
    }
}