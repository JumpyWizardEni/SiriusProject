package com.siriusproject.coshelek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val catTitle: TextView = itemView.findViewById(R.id.cat_title)
    private val catImage: ImageView = itemView.findViewById(R.id.cat_img)
    fun bind(item: CategoryModel) {
        catTitle.text = item.name
        catImage.setImageDrawable(item.picture)
    }
}

class CategoriesListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categories: MutableList<CategoryModel> = mutableListOf()

    fun setData(newCats: MutableList<CategoryModel>) {
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

    private fun onClicked(cat: CategoryModel) {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(categories[position])
    }

    override fun getItemCount() = categories.size

}