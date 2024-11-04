package com.app.dogedex.doglist

import com.app.dogedex.R
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.response.Dog
import com.app.dogedex.api.DogsApi.retrofitService
import com.app.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {

        return withContext(Dispatchers.IO){
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                ApiResponseStatus.Success(dogDTOMapper.fromDogDTOListToDomainList(dogDTOList))
            }catch (e: UnknownHostException){
                ApiResponseStatus.Error(R.string.unknown_host_exception_error)
            }catch (e: Exception){
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }
}