package com.app.dogedex.api.dto

import com.app.dogedex.model.User

class UserDTOMapper {
    fun fromUserDTOToUserDomain(userDTO: UserDTO): User =
         User(userDTO.id, userDTO.email, userDTO.authentucationToken)

}