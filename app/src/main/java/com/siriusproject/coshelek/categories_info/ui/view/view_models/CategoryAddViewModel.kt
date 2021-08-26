package com.siriusproject.coshelek.categories_info.ui.view.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.categories_info.domain.use_cases.AddCategoryUseCase
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.checkOperation
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryAddViewModel @Inject constructor(
    private val addCategoriesUseCase: AddCategoryUseCase,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    private val mutableIconState = MutableStateFlow<CategoryIcon?>(null)
    private val mutableColorState = MutableStateFlow<Int?>(null)
    private val mutableNameState = MutableStateFlow<String?>(null)
    private val mutableTypeState = MutableStateFlow<TransactionType?>(null)

    val iconState = mutableIconState as StateFlow<CategoryIcon?>
    val colorState = mutableColorState as StateFlow<Int?>
    val nameState = mutableNameState as StateFlow<String?>
    val typeState = mutableTypeState as StateFlow<TransactionType?>

    private val mutableAddCategoryState = MutableStateFlow(LoadingState.Initial)
    val addCategoryState = mutableAddCategoryState as StateFlow<LoadingState>

    val categoryCreateState = combine(
        mutableNameState,
        mutableTypeState,
        mutableIconState,
        mutableColorState
    ) { name, type, icon, color ->
        name != null && type != null && icon != null && color != null
    }

    fun onCategoryCreatePressed(finishActivity: () -> Unit) {
        val icon = mutableIconState.value ?: return
        val color = mutableColorState.value ?: return
        val name = mutableNameState.value ?: return
        val type = mutableTypeState.value ?: return
        viewModelScope.launch {
            checkOperation(mutableAddCategoryState) {
                addCategoriesUseCase.addCategory(name, type, icon, color)
                finishActivity()
            }
        }
    }

    fun onCategoryTitleButton() {
        navigationDispatcher.emit {
            it.navigate(R.id.action_AddCategoryFragment_to_EnterNameFragment)
        }
    }

    fun onCategoryTypeButton() {
        navigationDispatcher.emit {
            it.navigate(R.id.action_AddCategoryFragment_to_EnterTypeFragment)
        }
    }

    fun pushCategoryIcon(icon: CategoryIcon) {
        Log.d("NEW_CATEGORY", "selected icon: ${icon.drawable}")
        mutableIconState.value = icon
    }

    fun pushCategoryColor(color: Int) {
        Log.d("NEW_CATEGORY", "selected color: $color")
        mutableColorState.value = color
    }

    fun onTypeReadyPressed() {
        navigationDispatcher.emit {
            it.navigate(R.id.action_enterTypeFragment_to_AddCategoryFragment)
        }
    }

    fun pushType(type: TransactionType) {
        Log.d("NEW_CATEGORY", "selected type: $type")
        mutableTypeState.value = type
    }

    fun onNameReadyPressed(name: String) {
        Log.d("NEW_CATEGORY", "selected name: $name")
        mutableNameState.value = name
        navigationDispatcher.emit {
            it.navigate(R.id.action_enterNameFragment_to_AddCategoryFragment)
        }
    }

}