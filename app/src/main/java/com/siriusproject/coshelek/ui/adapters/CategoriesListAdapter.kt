package com.siriusproject.coshelek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.data.model.CategoryUiModel

class CategoriesListAdapter(val onCategorySelected: (CategoryUiModel)->Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val catTitle: TextView = itemView.findViewById(R.id.cat_title)
        private val catImage: ImageView = itemView.findViewById(R.id.cat_img)
        fun bind(item: CategoryUiModel) {
            catTitle.text = item.name
            catImage.setImageDrawable(item.picture)
        }
    }

    private val categories: MutableList<CategoryUiModel> = mutableListOf()

    fun setData(newCats: MutableList<CategoryUiModel>) {
        categories.clear()
        categories.addAll(newCats)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val viewHolder = CategoryViewHolder(rootView)
        viewHolder.itemView.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION)
                onClicked(categories[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    private fun onClicked(cat: CategoryUiModel) {
        onCategorySelected(cat)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(categories[position])
    }

    override fun getItemCount() = categories.size

}