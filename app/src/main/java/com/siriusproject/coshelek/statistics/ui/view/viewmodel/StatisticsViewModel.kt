package com.siriusproject.coshelek.statistics.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siriusproject.coshelek.statistics.data.model.CategoryStatistic
import com.siriusproject.coshelek.statistics.domain.use_cases.GetStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatistics: GetStatistics
) : ViewModel() {

    private val mutableCatStats = MutableStateFlow<List<CategoryStatistic>?>(null)
    val catStats = mutableCatStats.filterNotNull()

    init {
        viewModelScope.launch {
            getStatistics
                .getStatisticsByCategories()
                .collect {
                    mutableCatStats.value = it
                }
        }
    }
}