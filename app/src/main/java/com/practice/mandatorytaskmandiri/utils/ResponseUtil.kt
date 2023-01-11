package com.practice.mandatorytaskmandiri.utils

sealed class ResponseUtil<out R> {

    object Loading : ResponseUtil<Nothing>()

    data class Success<out T>(val data: T) : ResponseUtil<T>()

    object Empty : ResponseUtil<Nothing>()

    data class Error(val errorMessage: String) : ResponseUtil<Nothing>()
}
