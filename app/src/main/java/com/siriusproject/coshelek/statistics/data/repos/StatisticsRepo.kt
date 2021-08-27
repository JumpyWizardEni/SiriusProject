package com.siriusproject.coshelek.statistics.data.repos

import com.siriusproject.coshelek.statistics.data.remote.StatisticsRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StatisticsRepo @Inject constructor(
    val remote: StatisticsRemote
) {

    suspend fun getCategoryStatistics() = flow {
        emit(remote.getCategoriesStatistics())
    }.flowOn(Dispatchers.IO)

}