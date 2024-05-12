package com.example.onlinestore.core.models.RequestModel

import com.google.gson.annotations.SerializedName


data class PostProductModel(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("categoryId")
    val categoryId: Int?=null,
    @SerializedName("images")
    val images: List<String>? = null,
)
