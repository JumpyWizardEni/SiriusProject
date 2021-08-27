package com.siriusproject.coshelek.statistics.domain.use_cases

import com.siriusproject.coshelek.statistics.data.repos.StatisticsRepo
import javax.inject.Inject

class GetStatistics @Inject constructor(
    val repository: StatisticsRepo
) {

    suspend fun getStatisticsByCategories() = repository.getCategoryStatistics()

}