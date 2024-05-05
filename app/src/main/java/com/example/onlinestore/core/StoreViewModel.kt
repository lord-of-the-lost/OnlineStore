package com.example.onlinestore.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.onlinestore.core.api.NetworkService
import com.example.onlinestore.core.location.LocationUseCase
import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.repository.StoreRepository
import com.example.onlinestore.core.storage.AppDatabase
import com.example.onlinestore.core.storage.ProductDAO
import com.example.onlinestore.core.storage.UserDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.escuelajs.co/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productDAO: ProductDAO by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java, "products.db"
        ).build().productDao()
    }
    private val userDAO: UserDAO by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java, "users.db"
        ).build().userDao()
    }

    private val networkService = retrofit.create(NetworkService::class.java)

    private val repository: StoreRepository by lazy {
        StoreRepository(productDAO, userDAO)
    }

    private val locationUseCase: LocationUseCase by lazy {
        LocationUseCase(application)
    }

    private val _savedProducts = MutableStateFlow<List<ProductModel>?>(null)
    val savedProducts: StateFlow<List<ProductModel>?> = _savedProducts.asStateFlow()

    private val _currentCurrency = MutableStateFlow<Currency>(Currency.USD)
    val currentCurrency: StateFlow<Currency> = _currentCurrency.asStateFlow()

    private val _selectedCountry = MutableStateFlow("Америка")
    val selectedCountry: StateFlow<String> = _selectedCountry.asStateFlow()

    private val _favoriteProducts = MutableStateFlow<Set<Int>>(setOf())
    val favoriteProducts: StateFlow<Set<Int>> = _favoriteProducts.asStateFlow()

    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    private val _isUserManager = MutableStateFlow(false)
    val isUserManager: StateFlow<Boolean> = _isUserManager.asStateFlow()

    init {
        loadProducts()
        loadCategories()
    }

    private val _selectedProduct = MutableStateFlow<ProductModel?>(null)
    val selectedProduct: StateFlow<ProductModel?> = _selectedProduct

    fun setUserManagerStatus(isManager: Boolean) {
        _isUserManager.value = isManager
    }

    fun setSelectedProduct(product: ProductModel) {
        _selectedProduct.value = product
    }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            val product = _products.value.find { it.id == productId }
            product?.let {
                if (_favoriteProducts.value.contains(productId)) {
                    _favoriteProducts.value = _favoriteProducts.value.minus(productId)
                    deleteProduct(it)
                    _savedProducts.value = _savedProducts.value?.filterNot { p -> p.id == productId }
                } else {
                    _favoriteProducts.value = _favoriteProducts.value.plus(productId)
                    saveProduct(it)
                    _savedProducts.value = _savedProducts.value?.plus(it)
                }
            }
        }
    }

    // Location VM logic
    fun formatPriceWithCurrency(price: Double, currency: Currency): String {
        val (convertedPrice, currencySymbol) = when (currency) {
            Currency.USD -> price to "$"
            Currency.EUR -> (price * 0.9) to "€"
            Currency.RUB -> (price * 90) to "₽"
        }
        return "${currencySymbol}${convertedPrice.format(2)}"
    }

    fun setSelectedCountry(country: String) {
        if (_selectedCountry.value != country) {
            _selectedCountry.value = country
        }

        val newCurrency = when (country) {
            "Россия" -> Currency.RUB
            "Америка" -> Currency.USD
            "Европа" -> Currency.EUR
            else -> Currency.USD
        }

        if (_currentCurrency.value != newCurrency) {
            _currentCurrency.value = newCurrency
        }
    }

    //Storage Product VM logic
    fun saveProduct(product: ProductModel) {
        viewModelScope.launch {
            repository.saveProduct(product)
        }
    }

    fun getProductsFromDB() {
        viewModelScope.launch {
            val products = repository.getAllProducts()
            _savedProducts.value = products
        }
    }

    fun deleteProduct(product: ProductModel) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    //Auth VM logic
    private val _authStateState = MutableStateFlow<AuthState>(AuthState())
    val authState: StateFlow<AuthState> = _authStateState

    fun register(firstName: String, email: String, password: String, confirmPass: String) {
        if (password != confirmPass) {
            _authStateState.value = AuthState(error = "Passwords do not match")
            return
        }
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authStateState.value = AuthState(success = true)
                        } else {
                            _authStateState.value = AuthState(
                                error = task.exception?.message ?: "Registration failed"
                            )
                        }
                    }
            } catch (e: Exception) {
                _authStateState.value =
                    AuthState(error = e.message ?: "Registration failed")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authStateState.value = AuthState(success = true)
                        } else {
                            _authStateState.value = AuthState(
                                error = task.exception?.message ?: "Login failed"
                            )
                        }
                    }
            } catch (e: Exception) {
                _authStateState.value =
                    AuthState(error = e.message ?: "Login failed")
            }
        }
    }

    fun resetAuthState() {
        _authStateState.value = AuthState(success = false)
    }

    //Network VM logic
    fun deleteProductById(productId: Int) {
        viewModelScope.launch {
            try {
                networkService.deleteProduct(productId)
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = networkService.getAllProduct()
                _products.value = productList
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun loadProductById(id:Int) {
        viewModelScope.launch {
            try {
                val productList = networkService.getProductByCategory(id)
                _products.value = productList
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categoryList = networkService.getAllCategories()
                _categories.value = categoryList
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun sortProductsByPriceAscending() {
        _products.value = _products.value.sortedBy { it.price }
    }

    fun sortProductsByPriceDescending() {
        _products.value = _products.value.sortedByDescending { it.price }
    }

    fun sortProductsByTitleAscending() {
        _products.value = _products.value.sortedBy { it.title }
    }

    fun sortProductsByTitleDescending() {
        _products.value = _products.value.sortedByDescending { it.title }
    }
}

data class AuthState(
    val success: Boolean = false,
    val error: String = ""
)

fun Double.format(digits: Int) = "%.${digits}f".format(this)

enum class Currency {
    USD, EUR, RUB
}