package com.app.dogedex.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel: ViewModel() {

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set
    private val dogRepository = DogRepository()

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {


        status.value = apiResponseStatus
    }
}