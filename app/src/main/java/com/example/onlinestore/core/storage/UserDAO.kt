package com.example.onlinestore.core.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserObject)

    @Query("SELECT * FROM userobject u WHERE u.email = :email")
    suspend fun getUserByEmail(email: String): UserObject?
}