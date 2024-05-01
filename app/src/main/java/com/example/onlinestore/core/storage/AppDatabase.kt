package com.example.onlinestore.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductObject::class, UserObject::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
    abstract fun userDao(): UserDAO
}