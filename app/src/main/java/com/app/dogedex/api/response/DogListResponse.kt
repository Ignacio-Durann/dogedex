package com.app.dogedex.api.response

import com.squareup.moshi.Json

class DogListResponse (@field: Json(name = "dogs") val dogs: List<Dog>){

}