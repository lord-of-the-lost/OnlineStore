package com.example.onlinestore.views.HomeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.views.HomeScreen.network.Categories
import com.example.onlinestore.views.HomeScreen.network.Products
import com.example.onlinestore.views.HomeScreen.network.productService
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductViewModelTest : ViewModel() {

    private val _productState = mutableStateOf(ProductState())
    val productState: State<ProductState> = _productState
    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    init {
        fetchProducts()
        fetchCategories()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = productService.getAllProduct()
                _productState.value = _productState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _productState.value = _productState.value.copy(
                    loading = false,
                    error = "Error"
                )
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = productService.getAllCategories()
                _categoryState.value = _categoryState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _productState.value = _productState.value.copy(
                    loading = false,
                    error = "Error"
                )
            }
        }
    }


    data class ProductState(
        var loading: Boolean = true,
        var list: Products? = null,
        var error: String? = null

    )

    data class CategoryState(
        var loading: Boolean = true,
        var list: Categories? = null,
        var error: String? = null

    )

}