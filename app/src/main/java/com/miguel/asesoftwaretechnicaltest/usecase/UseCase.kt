package com.miguel.asesoftwaretechnicaltest.usecase

sealed class Result<out T>
data class Success<out T>(val data: T) : Result<T>()
data class Error(val message: String) : Result<Nothing>()

abstract class UseCase<in Params, out T> {

    abstract suspend fun execute(params: Params): Result<T>
}