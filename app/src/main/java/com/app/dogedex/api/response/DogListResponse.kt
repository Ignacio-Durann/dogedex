package com.app.dogedex.api.response

import com.app.dogedex.api.dto.DogDTO
import com.squareup.moshi.Json

class DogListResponse (@field: Json(name = "dogs") val dogs: List<DogDTO>){

}