package com.example.onlinestore.core.repository

import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.storage.ProductDAO
import com.example.onlinestore.core.storage.ProductObject
import com.example.onlinestore.core.storage.UserDAO
import com.example.onlinestore.core.storage.UserObject

class StoreRepository(private val productDAO: ProductDAO, private val userDAO: UserDAO) {

    suspend fun insertProduct(products: List<ProductModel>) {
        val productObjects = products.map { product ->
            ProductObject(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                images = product.images,
                creationAt = product.creationAt,
                updatedAt = product.updatedAt,
                category = product.category
            )
        }
        productDAO.insertProductList(productObjects)
    }

    suspend fun getAllProducts(): List<ProductModel>? {
        return productDAO.getAllProducts()?.map { productObject ->
            ProductModel(
                id = productObject.id,
                title = productObject.title,
                price = productObject.price,
                description = productObject.description,
                images = productObject.images,
                creationAt = productObject.creationAt,
                updatedAt = productObject.updatedAt,
                category = productObject.category
            )
        }
    }

    suspend fun saveUser(user: UserObject) {
        userDAO.saveUser(user)
    }
    suspend fun getUserByEmail(email: String): UserObject? {
        return userDAO.getUserByEmail(email)
    }
}