package com.siriusproject.coshelek.categories_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.categories_info.ui.viewholders.CategoryIconViewHolder
import com.siriusproject.coshelek.databinding.IcCategoryBinding

class CategoriesIconListAdapter(val onIconSelected: (CategoryIcon) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var icons = listOf<CategoryIcon>()

    companion object {
        private const val DEFAULT_SELECTED_ICON = 0
        private const val DEFAULT_COLOR = 0
    }

    private var selectedIcon = DEFAULT_SELECTED_ICON
    private var color = DEFAULT_COLOR

    fun setIcons(icons: List<CategoryIcon>) {
        this.icons = icons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IcCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CategoryIconViewHolder(binding)
        binding.root.setOnClickListener {
            if (viewHolder.bindingAdapterPosition != RecyclerView.NO_POSITION)
                onClicked(icons[viewHolder.bindingAdapterPosition])
        }
        return viewHolder
    }

    private fun onClicked(categoryIcon: CategoryIcon) {
        val newSelectedPos = icons.indexOf(categoryIcon)
        notifyItemChanged(selectedIcon, false)
        selectedIcon = newSelectedPos
        notifyItemChanged(selectedIcon, true)
        onIconSelected(categoryIcon)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val selected = position == selectedIcon
        (holder as CategoryIconViewHolder).bind(icons[position], color, selected)
        if (selected) onIconSelected(icons[position])
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) onBindViewHolder(holder, position)
        else {
            when (payloads[0]) {
                is Int -> (holder as CategoryIconViewHolder).changeColor(
                    payloads[0] as Int,
                    position == selectedIcon
                )
                is Boolean -> (holder as CategoryIconViewHolder).select(payloads[0] as Boolean)
            }

        }
    }

    override fun getItemCount() = icons.size

    fun updateIconsColor(color: Int) {
        this.color = color
        for (i in icons.indices)
            notifyItemChanged(i, color)
    }

    fun setCurrentIcon(icon: CategoryIcon) {
        onClicked(icon)
    }

}