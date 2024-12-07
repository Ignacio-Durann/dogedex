package com.app.dogedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.doglist.DogRepository
import com.app.dogedex.model.Dog
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val dogRepository = DogRepository()

    private val _dogList = MutableLiveData<Dog>()
    val dogList: LiveData<Dog>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status



    fun getDogByMlId(mlDogId: String){
        viewModelScope.launch {
           handleResponseStatus(dogRepository.getDogByMlId(mlDogId))
        }
    }



    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.data
        }

        _status.value = apiResponseStatus
    }
}