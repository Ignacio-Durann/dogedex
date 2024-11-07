package com.app.dogedex.doglist

import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.DogsApi
import com.app.dogedex.model.Dog
import com.app.dogedex.api.dto.DogDTOMapper
import com.app.dogedex.api.makeNetworkCall

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = DogsApi.retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDomainList(dogDTOList)
    }


}