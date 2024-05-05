package com.example.onlinestore.core.api

import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.ProductModel
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkService {
    @GET("products")
    suspend fun getAllProduct(): List<ProductModel>

    @GET("categories")
    suspend fun getAllCategories(): List<CategoryModel>

    @GET("categories/{id}")
    suspend fun getSingleCategory(@Path("id") id: Int): CategoryModel

    @GET("categories/{id}/products")
    suspend fun getProductByCategory(@Path("id") id: Int): List<ProductModel>

    @POST("categories/")
    suspend fun createCategory()

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)

    @DELETE("category/{id}")
    suspend fun deleteCategory(@Path("id") id: Int)

}