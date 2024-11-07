package com.app.dogedex.api.response

import com.squareup.moshi.Json

class AuthApiResponse (
    @field: Json(name = "message")
    val message: String,
    @field: Json(name = "is_success")
    val isSuccess: Boolean,
    @field: Json(name = "data")
    val data: UserResponse,
)