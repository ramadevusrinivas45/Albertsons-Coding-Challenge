package com.example.acronyms.ui

import com.example.acronyms.data.ApiService
import com.example.acronyms.data.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAcronyms(abbreviation: String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getAcronyms(abbreviation)
       emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}