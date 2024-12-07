package com.app.dogedex.doglist

import com.app.dogedex.R
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.DogsApi
import com.app.dogedex.api.DogsApi.retrofitService
import com.app.dogedex.api.dto.AddDogToUserDTO
import com.app.dogedex.model.Dog
import com.app.dogedex.api.dto.DogDTOMapper
import com.app.dogedex.api.makeNetworkCall
import com.app.dogedex.api.response.DefaultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {

        return withContext(Dispatchers.IO) {

            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            when {
                allDogsListResponse is ApiResponseStatus.Error -> {
                    allDogsListResponse
                }

                userDogsListResponse is ApiResponseStatus.Error -> {
                    userDogsListResponse
                }

                allDogsListResponse is ApiResponseStatus.Success &&
                        userDogsListResponse is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(
                        getCollectionList(
                            allDogsListResponse.data,
                            userDogsListResponse.data
                        )
                    )
                }

                else -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
            }
        }

    }

    private fun getCollectionList(allDogsList: List<Dog>, userDogList: List<Dog>)= allDogsList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(0, it.index, "", "", "", "", "", "", ""
                    , "", "", "", "", "", "", "", inCollection = false)
            }

    }.sorted()

    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = DogsApi.retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitService.addDogUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = DogsApi.retrofitService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDomainList(dogDTOList)
    }

    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val response = retrofitService.getDogByMlId(mlDogId)

        if (!response.isSuccess){
            throw Exception(response.message)
        }

        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOtoDogDomain(response.data.dog)

    }

}