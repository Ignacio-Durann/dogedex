package com.app.dogedex.auth

import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.DogsApi.retrofitService
import com.app.dogedex.api.dto.SignUpDTO
import com.app.dogedex.api.dto.UserDTOMapper
import com.app.dogedex.api.makeNetworkCall
import com.app.dogedex.model.User

class AuthRepository {
    //metodo que verifica si el susuario existe ya en el registro
    suspend fun logIn(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall{
        val loginDTO = LoginDTO(email, password)
        val loginResponse = retrofitService.login(loginDTO)

        if (!loginResponse.isSuccess){
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

    //metodo que manda ejecutar el servicio de registrar usuario
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