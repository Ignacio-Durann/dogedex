package com.app.dogedex.doglist

import com.app.dogedex.api.response.Dog
import com.app.dogedex.api.DogsApi.retrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    suspend fun downloadDogs(): List<Dog>{
        return withContext(Dispatchers.IO){
            val dogListApiResponse = retrofitService.getAllDogs()
            dogListApiResponse.data.dogs
        }
    }
}