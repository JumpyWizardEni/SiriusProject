package com.siriusproject.coshelek.categories_info.ui.view.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryAddViewModel @Inject constructor(
    //private val getCategoriesUseCase: GetCategories,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    fun onCategoryCreatePressed() {

    }

    fun onCategoryTitleButton() {

    }

    fun onCategoryTypeButton() {

    }

    fun pushCategoryIcon(icon: CategoryIcon) {
        Log.d("NEW_CATEGORY", "selected icon: ${icon.drawable}")
    }

    fun pushCategoryColor(color: Int) {
        Log.d("NEW_CATEGORY", "selected color: $color")
    }

}