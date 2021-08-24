package com.siriusproject.coshelek.wallet_list.data.remote

import java.net.ConnectException

sealed class Result<out R> {

    data class Success<out T>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class NoConnection(val exception: ConnectException) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is NoConnection -> "NoConnection[exception=$exception]"
            is Loading -> "Loading"
        }
    }

}