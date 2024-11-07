package com.app.dogedex.api.dto

import com.app.dogedex.api.response.DogListResponse
import com.squareup.moshi.Json

class UserDTO(
    @field: Json(name = "id")
    val id: Long,
    @field: Json(name = "email")
    val email: String,
    @field: Json(name = "authentication_token")
    val authentucationToken: String,
)