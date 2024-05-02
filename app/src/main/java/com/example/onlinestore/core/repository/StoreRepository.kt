package com.example.onlinestore.core.repository

import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.storage.ProductDAO
import com.example.onlinestore.core.storage.ProductObject
import com.example.onlinestore.core.storage.UserDAO
import com.example.onlinestore.core.storage.UserObject

class StoreRepository(private val productDAO: ProductDAO, private val userDAO: UserDAO) {
    suspend fun saveProduct(product: ProductModel) {
        productDAO.saveProduct(product.toProductObject())
    }

    suspend fun getAllProducts(): List<ProductModel>? {
        return productDAO.getAllProducts()?.map { productObject ->
            productObject.toProductModel()
        }
    }

    suspend fun deleteProduct(product: ProductModel) {
        productDAO.deleteProduct(product.toProductObject())
    }

    suspend fun saveUser(user: UserObject) {
        userDAO.saveUser(user)
    }
    suspend fun getUserByEmail(email: String): UserObject? {
        return userDAO.getUserByEmail(email)
    }
}

// расширения для мапинга моделей
fun ProductObject.toProductModel(): ProductModel {
    return ProductModel(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        images = this.images,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt,
        category = this.category
    )
}

fun ProductModel.toProductObject(): ProductObject {
    return ProductObject(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        images = this.images,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt,
        category = this.category
    )
}