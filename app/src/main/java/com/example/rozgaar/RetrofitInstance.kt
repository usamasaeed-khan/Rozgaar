package com.example.rozgaar

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Singleton Class
object RetrofitInstance {

    private const val BASE_URL="http://10.0.2.2:8091/User/"



    val instance:APIService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIService::class.java)

    }



}