package com.app.dogedex.api

import com.app.dogedex.api.dto.AddDogToUserDTO
import com.app.dogedex.api.dto.SignUpDTO
import com.app.dogedex.api.response.DogListApiResponse
import com.app.dogedex.api.response.AuthApiResponse
import com.app.dogedex.api.response.DefaultResponse
import com.app.dogedex.api.response.DogApiResponse
import com.app.dogedex.auth.LoginDTO
import com.app.dogedex.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Nivel de logging (BODY para detalles completos)
}


// Define la instancia de Retrofit dentro del objeto DogsApi
object DogsApi {

    // Crear el servicio de API con Retrofit
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

// Define la interfaz de API
interface ApiService {
    //obtiene todos los dogs de la base de datos
    @GET("dogs")
    suspend fun getAllDogs(): DogListApiResponse

    //agrega un nuevo usuario
    @POST("sign_up")
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    //se loguea un usuario ya existente
    @POST("sign_in")
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST("add_dog_to_user")
    suspend fun addDogUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET("get_user_dogs")
    suspend fun getUserDogs(): DogListApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET("find_dog_by_ml_id")
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): DogApiResponse

}
