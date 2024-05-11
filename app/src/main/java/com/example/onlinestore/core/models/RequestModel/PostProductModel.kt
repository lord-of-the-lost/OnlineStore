package com.example.onlinestore.core.models.RequestModel

import com.google.gson.annotations.SerializedName


data class PostProductModel(
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("images")
    val images: List<String>,
    )
