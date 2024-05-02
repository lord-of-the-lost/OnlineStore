package com.example.onlinestore.core.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(product: ProductObject)

    @Delete
    suspend fun deleteProduct(product: ProductObject)

    @Query("SELECT * FROM productobject")
    suspend fun getAllProducts(): List<ProductObject>?
}