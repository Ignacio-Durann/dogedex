package com.app.dogedex.doglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dogedex.api.response.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {
    private val _dogList = MutableLiveData<List<Dog>>()


    val dogList: LiveData<List<Dog>>
            get() = _dogList


    private val dogRepository = DogRepository()

    init {
        downloadDogs()
    }

    private fun downloadDogs(){
        viewModelScope.launch() {
            try {
                _dogList.value = dogRepository.downloadDogs()
            }catch (e: Exception){
                Log.e("DogListViewModel", "Error al descargar la lista de perros: " + e)

            }

        }
    }
}