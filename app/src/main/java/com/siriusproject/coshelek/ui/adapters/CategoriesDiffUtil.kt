package com.siriusproject.coshelek.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.siriusproject.coshelek.data.model.CategoryUiModel

class CategoriesDiffUtil(
    private val oldList: List<CategoryUiModel>,
    private val newList: List<CategoryUiModel>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize()= newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].equals(newList[newItemPosition])
}