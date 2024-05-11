package com.example.onlinestore.core

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.onlinestore.core.api.NetworkService
import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.PostProductModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.repository.StoreRepository
import com.example.onlinestore.core.storage.AppDatabase
import com.example.onlinestore.core.storage.ProductDAO
import com.example.onlinestore.core.storage.UserDAO
import com.example.onlinestore.views.CartScreen.ShopItem
import com.example.onlinestore.views.search_screen.HistoryItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    private val _savedProducts = MutableStateFlow<Set<ProductModel>?>(null)
    val savedProducts: StateFlow<Set<ProductModel>?> = _savedProducts.asStateFlow()

    private val _currentCurrency = MutableStateFlow<Currency>(Currency.USD)
    val currentCurrency: StateFlow<Currency> = _currentCurrency.asStateFlow()

    private val _selectedCountry = MutableStateFlow("Америка")
    val selectedCountry: StateFlow<String> = _selectedCountry.asStateFlow()

    private val _favoriteProducts = MutableStateFlow<Set<Int>>(setOf())
    val favoriteProducts: StateFlow<Set<Int>> = _favoriteProducts.asStateFlow()

    private val _allProducts = MutableStateFlow<List<ProductModel>>(listOf())
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> = _products.asStateFlow()

    private val _minPrice = MutableStateFlow(0f)
    val minPrice: StateFlow<Float> = _minPrice.asStateFlow()

    private val _maxPrice = MutableStateFlow(0f)
    val maxPrice: StateFlow<Float> = _maxPrice.asStateFlow()

    private val _selectedMinPrice = MutableStateFlow<Float?>(null)
    val selectedMinPrice: StateFlow<Float?> = _selectedMinPrice.asStateFlow()

    private val _selectedMaxPrice = MutableStateFlow<Float?>(null)
    val selectedMaxPrice: StateFlow<Float?> = _selectedMaxPrice.asStateFlow()

    private val _productsOnSearch = MutableStateFlow<List<ProductModel>>(emptyList())
    val productsOnSearch: StateFlow<List<ProductModel>> = _productsOnSearch.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    private var _searchString = MutableStateFlow("")
    var searchSting: StateFlow<String> = _searchString.asStateFlow()

    private var _categoryId = MutableStateFlow(1)
    var categoryId:StateFlow<Int> = _categoryId.asStateFlow()

    fun updateCategoryID(id:Int){
        _categoryId.value = id
    }


    private var _historyList = mutableStateListOf<HistoryItem>()
    fun clearProductSearch() {
        _productsOnSearch.value = emptyList()
    }

    val historyList: List<HistoryItem>
        get() = _historyList

    fun updateSearch(str: String) {
        _searchString.value = str
    }

    fun deleteSearch() {
        _searchString.value = ""
    }

    fun updateHistory(item: HistoryItem) {
        _historyList.add(item)
    }

    fun deleteHistory(item: HistoryItem) {
        _historyList.remove(item)
    }

    fun deleteAllHistory() {
        _historyList.clear()
    }


    private val _isUserManager = MutableStateFlow(false)
    val isUserManager: StateFlow<Boolean> = _isUserManager.asStateFlow()

    private val _cartProducts = MutableStateFlow<List<ProductModel>>(emptyList())
    val cartProducts: StateFlow<List<ProductModel>> = _cartProducts.asStateFlow()

    init {
        loadProducts()
        loadCategories()
    }

    private var _cartSize = MutableStateFlow(0)
    val cartSize: StateFlow<Int> = _cartSize.asStateFlow()


    private val _selectedProduct = MutableStateFlow<ProductModel?>(null)
    val selectedProduct: StateFlow<ProductModel?> = _selectedProduct

    fun setUserManagerStatus(isManager: Boolean) {
        _isUserManager.value = isManager
    }

    fun setSelectedProduct(product: ProductModel) {
        _selectedProduct.value = product
    }

    fun addToCart(product: ProductModel) {
        _cartProducts.update { currentProducts ->
            currentProducts + product
        }
        _cartSize.value = _cartProducts.value.size
    }

    fun removeFromCart(productId: Int) {
        _cartProducts.update { currentProducts ->
            currentProducts.filterNot { it.id == productId }
        }
        _cartSize.value = _cartProducts.value.size
    }

    val shopItemsInCart: StateFlow<List<ShopItem>> =
        cartProducts.combine(currentCurrency) { cartItems, currency ->
            cartItems.map { product ->
                ShopItem(
                    productId = product.id,
                    imgOfProduct = product.images.firstOrNull(),
                    nameOfProduct = product.title,
                    priceOfProduct = formatPriceWithCurrency(product.price.toDouble(), currency),
                    quantity = 1,
                    isSelected = false
                )
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            val product = _allProducts.value.find { it.id == productId }
            product?.let {
                if (_favoriteProducts.value.contains(productId)) {
                    _favoriteProducts.value = _favoriteProducts.value.minus(productId)
                    deleteProduct(it)
                    _savedProducts.value = _savedProducts.value?.minus(it)
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
            _savedProducts.value = products?.toSet()
        }
    }

    fun deleteProduct(product: ProductModel) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    suspend fun loadProductById(id: Int): ProductModel {
        return networkService.getProductByID(id)
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
                _allProducts.value = productList
                _minPrice.value = productList.minByOrNull { it.price }?.price?.toFloat() ?: 0f
                _maxPrice.value = productList.maxByOrNull { it.price }?.price?.toFloat() ?: 0f
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun loadProductsByCategoryId(id: Int) {
        viewModelScope.launch {
            try {
                val productList = networkService.getProductByCategory(id)
                _allProducts.value = productList
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun loadProductByName(name: String) {
        viewModelScope.launch {
            try {
                val productList = networkService.productByName(name)
                _productsOnSearch.value = productList
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

    fun postNewProduct(product: PostProductModel) {
        viewModelScope.launch {
            try {
                networkService.createProduct(product)
            } catch (e: Exception) {
                println(e.message)
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

    fun filterProductsByPriceRange(min: Float, max: Float) {
        val filteredProducts = _allProducts.value.filter {
            val price = it.price.toFloat()
            price in min..max
        }
        _products.value = filteredProducts
        _selectedMinPrice.value = min
        _selectedMaxPrice.value = max
    }

    //image switch
    private val _bitmap = mutableStateOf<Bitmap?>(null)
    val bitmap = _bitmap
    fun onTakePhoto(bitmap: Bitmap) {
        _bitmap.value = bitmap
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
