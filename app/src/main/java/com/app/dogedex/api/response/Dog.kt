package com.app.dogedex.api.response

import com.squareup.moshi.Json

data class Dog(
val id: Long,
val index: Int,
val nameEs: String,
val nameEn: String,
val dogType: String,
val heightFemale: String,
val heightMale: String,
val imageUrl: String,
val lifeExpentancy: String,
val temperament: String,
val temperamentEn: String,
val weightMale: String,
val weightFemale: String,
val createdAt: String,
val updateAp: String,
val mlId: String,
)