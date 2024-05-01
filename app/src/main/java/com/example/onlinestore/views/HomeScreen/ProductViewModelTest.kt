package com.example.onlinestore.views.HomeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.views.HomeScreen.network.Products
import com.example.onlinestore.views.HomeScreen.network.productService
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductViewModelTest : ViewModel() {

    private val _productState = mutableStateOf(ProductState())
    val productState:State<ProductState> = _productState

    init {
     fetch()
   }

   private fun fetch(){
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
        var list: Products? = null,
        var error: String? = null

    )


}