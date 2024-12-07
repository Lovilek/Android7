package com.example.android7.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val matchService: MatchService by lazy {
        Retrofit.Builder()
            .baseUrl(MatchService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MatchService::class.java)
    }
}
