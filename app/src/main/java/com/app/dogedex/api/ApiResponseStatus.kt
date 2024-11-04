package com.app.dogedex.api

import com.app.dogedex.api.response.Dog

sealed class ApiResponseStatus() {
    class Success(val dogList: List<Dog>): ApiResponseStatus()
    class Loading(): ApiResponseStatus()
    class Error(val messageId: Int): ApiResponseStatus()

}