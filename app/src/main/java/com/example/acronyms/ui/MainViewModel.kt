package com.example.acronyms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronyms.data.AcronymsResponse
import com.example.acronyms.data.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var _acronymResponse = MutableLiveData<NetworkResult<List<AcronymsResponse>>>()
    val acronymResponse: LiveData<NetworkResult<List<AcronymsResponse>>> = _acronymResponse

    fun fetchAcronyms(abbreviation: String) {
        viewModelScope.launch {
            mainRepository.getAcronyms(abbreviation).collect {
                _acronymResponse.postValue(it)
            }
        }
    }
}

