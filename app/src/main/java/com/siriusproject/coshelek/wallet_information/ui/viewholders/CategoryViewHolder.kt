package com.siriusproject.coshelek.wallet_information.ui.viewholders

import android.content.res.ColorStateList
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.databinding.CategoryItemBinding

class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CategoryUiModel, selected: Boolean) {
        with(binding){
            catTitle.text = item.name
            catImg.categoryIcon.setImageResource(item.picture)
            catImg.categoryIcon.backgroundTintList = ColorStateList.valueOf(item.color)
            select(selected)
        }
    }
    fun select(selected: Boolean){
        binding.selectedCatIcon.visibility = if(selected) View.VISIBLE else View.GONE
    }
}