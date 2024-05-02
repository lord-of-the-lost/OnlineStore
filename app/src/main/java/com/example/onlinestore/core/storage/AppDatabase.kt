package com.example.onlinestore.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.onlinestore.core.models.Converters

@Database(
    entities = [ProductObject::class, UserObject::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
    abstract fun userDao(): UserDAO
}