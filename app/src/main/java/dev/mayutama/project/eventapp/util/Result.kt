package dev.mayutama.project.eventapp.util

sealed class Result<out R> private constructor() {
    data class Success<out R>(val data: R): Result<R>()
    data class Error(val error: String): Result<Nothing>()
    object Loading: Result<Nothing>()
}