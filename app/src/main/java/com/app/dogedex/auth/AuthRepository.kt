package com.app.dogedex.auth

import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.DogsApi.retrofitService
import com.app.dogedex.api.dto.DogDTOMapper
import com.app.dogedex.api.dto.SignUpDTO
import com.app.dogedex.api.dto.UserDTOMapper
import com.app.dogedex.api.makeNetworkCall
import com.app.dogedex.model.User

class AuthRepository {
    suspend fun signUp(email: String, password: String, passwordConfirmation: String): ApiResponseStatus<User> = makeNetworkCall{
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess){
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }
}