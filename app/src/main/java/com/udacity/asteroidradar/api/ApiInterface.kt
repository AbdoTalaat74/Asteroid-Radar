package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

interface ImgApiInterface {
    @GET("planetary/apod?api_key=T6cHWofGW6F5BEBoDPbp3wLdaLHRedN9ytve3ddr")
    suspend fun getPhoto(): PictureOfDay
}

object ImgAsteroidApi {

    val retrofitService: ImgApiInterface by lazy { retrofit.create(ImgApiInterface::class.java) }
}

//interface DataApiInterface{
//    @GET
//}

