package com.volie.artbookhilttesting.api

import com.volie.artbookhilttesting.model.ImageResponce
import com.volie.artbookhilttesting.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<ImageResponce>
}