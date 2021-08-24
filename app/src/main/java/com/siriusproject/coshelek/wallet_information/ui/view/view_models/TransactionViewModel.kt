package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategories
import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories,
    private val repos: TransactionsRepository,
    private val navigationDispatcher: NavigationDispatcher
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
            viewModelScope.launch {
                repos.createTransaction(
                    0,
                    amount.value!!.toBigDecimal(),
                    type.value!!,
                    category.value!!.name,
                    "RUB",
                    LocalDateTime.now()
                )
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_operationChangeFragment_to_walletFragment)
                }
            }
        }
    }

    fun onSumReadyPressed() {
        navigationDispatcher.emit { navController ->
            navController.navigate(R.id.action_enterSumFragment_to_typeOperationFragment)
        }
    }

    fun onTypeReadyPressed() {
        navigationDispatcher.emit { navController ->
            navController.navigate(R.id.action_typeOperationFragment_to_categorySelectFragment)
        }
    }

    fun onCategoryReadyPressed() {
        navigationDispatcher.emit { navController ->
            navController.navigate(R.id.action_categorySelectFragment_to_operationChangeFragment)
        }
    }

}