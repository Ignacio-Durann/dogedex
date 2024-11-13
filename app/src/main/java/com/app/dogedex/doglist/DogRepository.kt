package com.app.dogedex.doglist

import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.DogsApi
import com.app.dogedex.api.DogsApi.retrofitService
import com.app.dogedex.api.dto.AddDogToUserDTO
import com.app.dogedex.model.Dog
import com.app.dogedex.api.dto.DogDTOMapper
import com.app.dogedex.api.makeNetworkCall
import com.app.dogedex.api.response.DefaultResponse

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = DogsApi.retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitService.addDogUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess){
            throw Exception(defaultResponse.message)
        }
    }

    suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = DogsApi.retrofitService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDomainList(dogDTOList)
    }

}