package com.app.dogedex.api.dto

import com.squareup.moshi.Json

class SignUpDTO(
    @field: Json(name = "email")
    val email: String,

    @field: Json(name = "password")
    val password: String,

    @field: Json(name = "password_confirmation")
    val passwordConfirmation: String
)