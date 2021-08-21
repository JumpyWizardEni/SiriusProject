package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories,
    private val repos: TransactionsRepository
) : ViewModel() {

    private val mutableAmount = MutableStateFlow<String?>(null)
    private val mutableType = MutableStateFlow<TransactionType?>(null)
    private val mutableCategory = MutableStateFlow<CategoryUiModel?>(null)

    val amount = mutableAmount as StateFlow<String?>
    val type = mutableType as StateFlow<TransactionType?>
    val category = mutableCategory as StateFlow<CategoryUiModel?>

    private val mutableCategories =
        MutableStateFlow<LoadResult<List<CategoryUiModel>>>(LoadResult.Loading)
    val categories: Flow<LoadResult<List<CategoryUiModel>>> =
        mutableCategories
            .combine(mutableType.filterNotNull()) { categories, type ->
                categories.map { list -> list.filter { it.type == type } }
            }

    init {
        viewModelScope.launch {
            getCategoriesUseCase
                .getCategoriesForUI()
                .collect {
                    mutableCategories.value = it
                }
        }
    }

    fun pushAmount(amount: String?) {
        this.mutableAmount.value = amount
    }

    fun pushType(type: TransactionType?) {
        this.mutableType.value = type
    }

    fun pushCategory(category: CategoryUiModel?) {
        this.mutableCategory.value = category
    }

    fun onCreateTransactionButton() {
        if (amount.value != null && type.value != null && category.value != null) {
            //TODO push new transaction through repository
        }
    }

}