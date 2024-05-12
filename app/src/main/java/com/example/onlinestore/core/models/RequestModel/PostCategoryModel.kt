package com.example.onlinestore.core.models.RequestModel

import com.google.gson.annotations.SerializedName

data class PostCategoryModel(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("image")
    var image: String? =null
)