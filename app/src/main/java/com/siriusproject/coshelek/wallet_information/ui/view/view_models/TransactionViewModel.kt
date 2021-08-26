package com.siriusproject.coshelek.wallet_information.ui.view.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategories
import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_information.ui.view.fragments.OperationEditFragment.Companion.EDIT_FRAGMENT
import com.siriusproject.coshelek.wallet_list.ui.view.LoadingState
import com.siriusproject.coshelek.wallet_list.ui.view.navigation.NavigationDispatcher
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories,
    private val repos: TransactionsRepository,
    private val navigationDispatcher: NavigationDispatcher, application: Application
) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val mutableAmount = MutableStateFlow<String?>(null)
    private val mutableType = MutableStateFlow<TransactionType?>(null)
    private val mutableCategory = MutableStateFlow<CategoryUiModel?>(null)
    private val loadingStateData = MutableStateFlow(LoadingState.Loading)
    private val dateData = MutableStateFlow(LocalDateTime.now())
    private val mutableCategories =
        MutableStateFlow<LoadResult<List<CategoryUiModel>>>(LoadResult.Loading)
    private val errorChannel = Channel<String>()
    private var transactionId = 0
    val errorFlow = errorChannel.receiveAsFlow()
    val date: StateFlow<LocalDateTime> = dateData
    val loadingState: StateFlow<LoadingState> = loadingStateData
    val amount = mutableAmount as StateFlow<String?>
    val type = mutableType as StateFlow<TransactionType?>
    val category = mutableCategory as StateFlow<CategoryUiModel?>
    var walletId = 0
    val categories: Flow<LoadResult<List<CategoryUiModel>>> =
        mutableCategories
            .combine(mutableType.filterNotNull()) { categories, type ->
                categories.map { list -> list.filter { it.type == type } }
            }

    init {
        viewModelScope.launch {
            checkOperation {
                getCategoriesUseCase
                    .getCategoriesForUI()
                    .collect {
                        Log.d(javaClass.name, it.toString())
                        mutableCategories.value = it
                    }
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

    fun onCreateTransactionPressed() {
        if (amount.value != null && type.value != null && category.value != null) {
            viewModelScope.launch {
                checkOperation {
                    repos.createTransaction(
                        walletId,
                        amount.value!!.toBigDecimal(),
                        type.value!!,
                        category.value!!.id,
                        "RUB",
                        date.value
                    )
                    navigationDispatcher.emit { navController ->
                        navController.navigate(R.id.action_operationChangeFragment_to_walletFragment)
                    }
                }
            }
        }
    }

    fun onSumReadyPressed(prevFragment: Int, currFragment: Int) {
        val data = Bundle()
        data.putInt(PREVIOUS_FRAGMENT, currFragment)
        if (prevFragment == R.layout.fragment_wallet_creating_info) {
            navigationDispatcher.emit { navController ->
                navController.navigate(
                    R.id.action_enterSumFragment_to_operationChangeFragment,
                    data
                )
            }
        } else if (prevFragment == R.layout.fragment_wallet) {
            navigationDispatcher.emit { navController ->
                navController.navigate(R.id.action_enterSumFragment_to_typeOperationFragment, data)
            }
        } else {
            navigationDispatcher.emit { navController ->

                navController.navigate(R.id.action_enterSumFragment_to_operationEditFragment, data)
            }
        }

    }

    fun onSumPressed(fragmentId: Int) {
        val data = Bundle()
        data.putInt(PREVIOUS_FRAGMENT, fragmentId)
        navigationDispatcher.emit { navController ->
            when (fragmentId) {
                R.layout.fragment_wallet_creating_info -> navController.navigate(
                    R.id.action_operationChangeFragment_to_sumEditFragment,
                    data
                )
                EDIT_FRAGMENT -> navController.navigate(
                    R.id.action_operationEditFragment_to_enterSumFragment,
                    data
                )
            }
        }
    }

    fun onTypeReadyPressed(previousFragment: Int) {
        val data = Bundle().apply {
            putInt(PREVIOUS_FRAGMENT, previousFragment)
        }
        navigationDispatcher.emit { navController ->
            navController.navigate(
                R.id.action_typeOperationFragment_to_categorySelectFragment,
                data
            )
        }
    }

    fun onTypePressed(fragmentId: Int) {
        val data = Bundle()
        data.putInt(PREVIOUS_FRAGMENT, fragmentId)
        navigationDispatcher.emit { navController ->
            when (fragmentId) {
                R.layout.fragment_wallet_creating_info -> navController.navigate(
                    R.id.action_operationChangeFragment_to_typeEditFragment,
                    data
                )
                EDIT_FRAGMENT -> navController.navigate(
                    R.id.action_operationEditFragment_to_typeOperationFragment,
                    data
                )
            }
        }
    }


    fun onCategoryReadyPressed(prevFragment: Int) {
        if (prevFragment == R.layout.fragment_wallet_creating_info) {
            navigationDispatcher.emit { navController ->
                navController.navigate(R.id.action_categorySelectFragment_to_operationChangeFragment)
            }
        } else if (prevFragment == R.layout.fragment_enter_sum) {
            navigationDispatcher.emit { navController ->
                navController.navigate(R.id.action_categorySelectFragment_to_operationChangeFragment)
            }
        } else {
            navigationDispatcher.emit { navController ->
                navController.navigate(R.id.action_categorySelectFragment_to_operationEditFragment)
            }
        }
    }

    fun onCategoryPressed(fragmentId: Int) {
        val data = Bundle()
        data.putInt(PREVIOUS_FRAGMENT, fragmentId)
        navigationDispatcher.emit { navController ->
            when (fragmentId) {
                R.layout.fragment_wallet_creating_info -> navController.navigate(
                    R.id.action_operationChangeFragment_to_categorySelectFragment,
                    data
                )
                EDIT_FRAGMENT -> navController.navigate(
                    R.id.action_operationEditFragment_to_categorySelectFragment,
                    data
                )
            }
        }
    }

    fun onEditTransactionPressed() {
        viewModelScope.launch {
            checkOperation {
                repos.editingTransaction(
                    transactionId,
                    amount.value?.toBigDecimal(),
                    type.value,
                    category.value?.id,
                    "RUB",
                    dateData.value
                )
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_operationEditFragment_to_walletFragment)
                }
            }
        }
    }

    suspend fun checkOperation(op: suspend () -> Unit) {
        try {
            loadingStateData.value = LoadingState.Loading
            op()
            loadingStateData.value = LoadingState.Ready
        } catch (e: Exception) {
            if (e is ConnectException) {
                loadingStateData.value = LoadingState.NoConnection
            } else {
                loadingStateData.value = LoadingState.UnexpectedError
            }
        }
    }

    fun setModel(model: TransactionUiModel) {
        mutableAmount.value = model.amount.toPlainString()
        mutableCategory.value = model.category
        mutableType.value = model.type
        transactionId = model.id
    }

    fun pushDateTime(date: LocalDateTime) {
        dateData.value = date
    }

    fun onQrReady(contents: String?) {
        try {
            if (contents == null) {
                errorChannel.trySend(context.getString(R.string.qr_error))
            } else {
                Log.d(javaClass.name, contents)
                val params = contents.split("&").associate {
                    val newList = it.split("=")
                    newList[0] to newList[1]
                }
                pushAmount(params["s"])
                pushType(TransactionType.Expense)
                val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm")
                pushDateTime(LocalDateTime.parse(params["t"], formatter))
                val data = Bundle()
                data.putInt(PREVIOUS_FRAGMENT, R.layout.fragment_enter_sum)
                navigationDispatcher.emit { navController ->
                    navController.navigate(R.id.action_enterSumFragment_to_categoryFragment, data)
                }
            }
        } catch (e: Exception) {
            errorChannel.trySend(context.getString(R.string.qr_error))
        }

    }


}