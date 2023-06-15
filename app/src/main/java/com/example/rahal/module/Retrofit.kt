package com.example.rahal.module

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    fun getRetrofitClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.14:3000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        //192.168.1.63
        //192.168.1.9
    }
}