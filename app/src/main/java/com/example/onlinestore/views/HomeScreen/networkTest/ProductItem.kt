package com.example.onlinestore.views.HomeScreen.networkTest


import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("price")
    var price: Int?  = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("images")
    var images: List<String>,
    @SerializedName("creationAt")
    var creationAt: String? = null,
    @SerializedName("updatedAt")
    var updatedAt: String? = null,
    @SerializedName("category")
    var category: Category? = Category()

)