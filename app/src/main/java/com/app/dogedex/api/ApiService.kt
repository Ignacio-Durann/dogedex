package com.app.dogedex.api
import com.app.dogedex.api.response.Dog
import com.app.dogedex.api.response.DogListApiResponse
import com.app.dogedex.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Define la instancia de Retrofit dentro del objeto DogsApi
object DogsApi {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Nivel de logging (BODY para detalles completos)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    // Crear el servicio de API con Retrofit
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

// Define la interfaz de API
interface ApiService {
    @GET("dogs")
    suspend fun getAllDogs(): DogListApiResponse
}
