package com.slaytertv.firegym.util

sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Sucess<out T>(val data:T): UiState<T>()
    data class Failure(val error:String?): UiState<Nothing>()
}