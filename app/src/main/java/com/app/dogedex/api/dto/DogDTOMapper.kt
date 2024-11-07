package com.app.dogedex.api.dto

import com.app.dogedex.model.Dog

class DogDTOMapper {

   private fun fromDogDTOtoDogDomain(dogDTO: DogDTO): Dog {
        return Dog(dogDTO.id, dogDTO.index, dogDTO.nameEs, dogDTO.nameEn, dogDTO.dogType,dogDTO.heightMale, dogDTO.heightFemale,
            dogDTO.imageUrl, dogDTO.lifeExpentancy, dogDTO.temperamentEn,dogDTO.temperament,dogDTO.weightFemale,dogDTO.weightMale,
            dogDTO.createdAt,dogDTO.updateAp,dogDTO.mlId)
    }

    fun fromDogDTOListToDomainList(dogDtOList: List<DogDTO>): List<Dog>{
        return dogDtOList.map { fromDogDTOtoDogDomain(it) }
    }
}