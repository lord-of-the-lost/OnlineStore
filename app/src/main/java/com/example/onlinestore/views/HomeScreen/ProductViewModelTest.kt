package com.example.onlinestore.views.HomeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.views.HomeScreen.networkTest.Product
import com.example.onlinestore.views.HomeScreen.networkTest.productService
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductViewModelTest : ViewModel() {

    private val _productState = mutableStateOf(ProductState())
    val productState:State<ProductState> = _productState


    fun fetch(){
        viewModelScope.launch {
            try {
                val response = productService.getAllProduct()
                _productState.value = _productState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )

            }catch (e:Exception){
                _productState.value = _productState.value.copy(
                    loading = false,
                    error = "Error"
                )
            }
        }
    }




    data class ProductState(
        var loading: Boolean = true,
        var list: Product? = null,
        var error: String? = null

    )


}