package com.example.onlinestore.core.api

import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.models.RequestModel.PostCategoryModel
import com.example.onlinestore.core.models.RequestModel.PostProductModel
import com.example.onlinestore.core.models.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("products")
    suspend fun getAllProduct(): List<ProductModel>

    @GET("categories")
    suspend fun getAllCategories(): List<CategoryModel>

    @GET("products/")
    suspend fun productByName(
        @Query("title") title: String
    ): List<ProductModel>

//    @GET("categories/{id}")
//    suspend fun getSingleCategory(@Path("id") id: Int): CategoryModel

    @GET("categories/{id}/products")
    suspend fun getProductByCategory(@Path("id") id: Int): List<ProductModel>

//    @GET("products/{id}")
//    suspend fun getProductByID(@Path("id") id: Int): ProductModel
    @POST("categories")
    suspend fun createCategory(@Body request: PostCategoryModel): Response<CategoryModel>
    @POST("products")
    suspend fun createProduct(@Body postRequest: PostProductModel): Response<ProductModel>
    @PUT("categories/{id}")
    suspend fun updateCategory(@Path("id") id:Int):Response<CategoryModel>
    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body postRequest: PostProductModel
    ): Response<ProductModel>
    @DELETE("category/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Response<ResponseData>
    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)





}