package com.app.dogedex.doglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.response.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {
    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
            get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status


    private val dogRepository = DogRepository()

    init {
        downloadDogs()
    }

    private fun downloadDogs(){
        viewModelScope.launch() {
            try {
                _status.value = ApiResponseStatus.LOADING
                _dogList.value = dogRepository.downloadDogs()
                _status.value = ApiResponseStatus.SUCCESS
            }catch (e: Exception){
               // Log.e("DogListViewModel", "Error al descargar la lista de perros: ")
                _status.value = ApiResponseStatus.ERROR
            }

        }
    }
}