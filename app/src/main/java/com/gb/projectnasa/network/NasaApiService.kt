package com.gb.projectnasa.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService {
    @GET("planetary/apod")
    suspend fun requestFotoDay(
        @Query("date") date: String,
        @Query("api_key") key: String
    ): NasaProperty

    @GET("planetary/apod")
    suspend fun requestListFoto(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") key: String,
        @Query("thumbs") thumbs: String
    ): List<NasaProperty>
}

object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}