package com.example.onlinestore.views.HomeScreen.network

import com.example.onlinestore.views.HomeScreen.network.model.Category
import com.example.onlinestore.views.HomeScreen.network.model.ProductItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getAllProduct(): Products

    @GET("categories")
    suspend fun getAllCategories(): Categories

    @GET("categories/{id}")
    suspend fun getSingleCategory(@Path("id") id: Int): Category

    @GET("categories/{id}/products")
    suspend fun getProductByCategory(): Products

    @POST("categories/")
    suspend fun createCategory()

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)

    @DELETE("category/{id}")
    suspend fun deleteCategory(@Path("id") id: Int)

}

var retrofit = Retrofit.Builder()
    .baseUrl("https://api.escuelajs.co/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var productService = retrofit.create(ApiService::class.java)