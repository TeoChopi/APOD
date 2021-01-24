package com.tapp.apod_app.repository.network

import com.tapp.apod_app.repository.model.Apod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApodApi {

    @GET("planetary/apod")  // planetary/apod?api_key=DEMO_KEY
    @Headers("Content-Type: application/json")
    fun getApod(@Query("api_key") apiKey: String): Call<Apod>


}