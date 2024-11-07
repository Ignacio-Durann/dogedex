package com.app.dogedex.auth

import com.squareup.moshi.Json

class LoginDTO(
    @field: Json(name = "email")
    val email: String,

    @field: Json(name = "password")
    val password: String,

    )