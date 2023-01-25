package com.example.acronyms.data

data class Acronyms(
    val lf: String,
    val freq: Int,
    val since: Int
)

data class AcronymsResponse(val lfs: List<Acronyms>, val sf: String)

sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}

