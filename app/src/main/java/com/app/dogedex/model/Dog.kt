package com.app.dogedex.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
val inCollection: Boolean = true
) : Parcelable, Comparable<Dog> {
    override fun compareTo(other: Dog) = if (this.index > other.index){
            1
        }else{
            -1
        }

}