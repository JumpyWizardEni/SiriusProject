package com.siriusproject.coshelek.categories_info.ui.viewholders

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.databinding.IcCategoryBinding
import com.siriusproject.coshelek.utils.darken

class CategoryIconViewHolder(private val binding: IcCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val SELECTED_BRIGHTNESS = 0.6f
    }

    private lateinit var icon: CategoryIcon
    private var color = 0

    fun bind(item: CategoryIcon, color: Int, selected: Boolean) {
        icon = item
        this.color = color
        with(binding) {
            categoryIcon.setImageResource(item.drawable)
            categoryIcon.backgroundTintList = ColorStateList.valueOf(color)
            select(selected)
        }
    }

    fun select(selected: Boolean) {
        val newColor = if (selected) darken(color, SELECTED_BRIGHTNESS) else color
        binding.categoryIcon.backgroundTintList = ColorStateList.valueOf(newColor)
    }

    fun changeColor(color: Int, selected: Boolean) {
        this.color = color
        select(selected)
    }

}