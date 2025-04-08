package com.app.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.doglist.DogRepository
import com.app.dogedex.machinelearning.Classifier
import com.app.dogedex.machinelearning.ClassifierRepository
import com.app.dogedex.machinelearning.DogRecognition
import com.app.dogedex.model.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel: ViewModel() {
    private val dogRepository = DogRepository()

    private val _dogList = MutableLiveData<Dog>()
    val dogList: LiveData<Dog>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    private lateinit var classifierRepository: ClassifierRepository


    fun setupClasifier(tfLiteModel: MappedByteBuffer,
                       labels: List<String>){

        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)

    }

    fun recognizeImage(imageProxy: ImageProxy){
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizeImage(imageProxy)
        imageProxy.close()
        }
    }

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