package com.example.onlinestore.views.HomeScreen.networkTest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface ApiServiceTest {
    @GET("products")
    suspend fun getAllProduct(): Product
}

var retrofit = Retrofit.Builder()
    .baseUrl("https://api.escuelajs.co/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var productService = retrofit.create(ApiServiceTest::class.java)