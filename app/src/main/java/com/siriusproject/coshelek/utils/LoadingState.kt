package com.siriusproject.coshelek.utils

import kotlinx.coroutines.flow.MutableStateFlow
import java.net.ConnectException

enum class LoadingState {
    Loading, UnexpectedError, Ready, NoConnection, Initial
}

suspend fun checkOperation(state: MutableStateFlow<LoadingState>, op: suspend () -> Unit) {
    try {
        state.value = LoadingState.Loading
        op()
        state.value = LoadingState.Ready
    } catch (e: Exception) {
        if (e is ConnectException) {
            state.value = LoadingState.NoConnection
        } else {
            state.value = LoadingState.UnexpectedError
        }
    }
}