package com.example.onlinestore.core.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun categoryToString(category: CategoryModel): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun stringToCategory(data: String): CategoryModel {
        val type = object : TypeToken<CategoryModel>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun cartItemListToString(cartItemList: List<CartItemModel>): String {
        return gson.toJson(cartItemList)
    }

    @TypeConverter
    fun stringToCartItemList(data: String): List<CartItemModel> {
        val type = object : TypeToken<List<CartItemModel>>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun stringListToString(data: List<String>): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromProductList(productList: List<ProductModel>): String {
        return gson.toJson(productList)
    }

    @TypeConverter
    fun stringToProductList(data: String): List<ProductModel> {
        val type = object : TypeToken<List<ProductModel>>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromByteArray(value: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(value, 0, value.size)
    }

    @TypeConverter
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            return toByteArray()
        }
    }
}
