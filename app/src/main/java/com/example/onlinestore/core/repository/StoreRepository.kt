package com.example.onlinestore.core.repository

import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.storage.UserDAO
import com.example.onlinestore.core.storage.UserObject

class StoreRepository(private val userDAO: UserDAO) {

    suspend fun saveUser(user: UserObject) {
        userDAO.saveUser(user)
    }
    suspend fun getUserByEmail(email: String): UserObject? {
        return userDAO.getUserByEmail(email)
    }
}