package com.example.onlinestore.core

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.onlinestore.core.api.NetworkService
import com.example.onlinestore.core.models.CartItemModel
import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.models.RequestModel.PostCategoryModel
import com.example.onlinestore.core.models.RequestModel.PostProductModel
import com.example.onlinestore.core.repository.StoreRepository
import com.example.onlinestore.core.storage.AppDatabase
import com.example.onlinestore.core.storage.UserDAO
import com.example.onlinestore.core.storage.UserObject
import com.example.onlinestore.views.CartScreen.ShopItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.escuelajs.co/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userDAO: UserDAO by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java, "users.db"
        ).build().userDao()
    }

    private val networkService = retrofit.create(NetworkService::class.java)

    private val repository: StoreRepository by lazy {
        StoreRepository(userDAO)
    }

    private val _currentUser = MutableStateFlow<UserObject?>(null)
    val currentUser: StateFlow<UserObject?> = _currentUser.asStateFlow()

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
    var categoryId: StateFlow<Int> = _categoryId.asStateFlow()

    fun updateCategoryID(id: Int) {
        _categoryId.value = id
    }

    private var _historyList = MutableStateFlow<List<String>>(emptyList())
    val historyList: StateFlow<List<String>> = _historyList.asStateFlow()

    fun clearProductSearch() {
        _productsOnSearch.value = emptyList()
    }

    fun updateSearch(str: String) {
        _searchString.value = str
    }

    fun deleteSearch() {
        _searchString.value = ""
    }

    fun deleteHistory(item: String) {
        viewModelScope.launch {
            val currentUser = _currentUser.value
            currentUser?.let { user ->
                val updatedSearchHistory = user.searchHistory.filter { it != item }
                val updatedUser = user.copy(searchHistory = updatedSearchHistory)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _historyList.value = updatedSearchHistory
            }
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            val currentUser = _currentUser.value
            currentUser?.let { user ->
                val updatedUser = user.copy(searchHistory = emptyList())
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _historyList.value = emptyList()
            }
        }
    }

    fun initializeSearchHistory() {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                _historyList.value = user.searchHistory
                _searchString.value = ""
            }
        }
    }

    private val _isUserManager = MutableStateFlow(false)
    val isUserManager: StateFlow<Boolean> = _isUserManager.asStateFlow()

    private val _cartProducts = MutableStateFlow<List<ProductModel>>(emptyList())
    val cartProducts: StateFlow<List<ProductModel>> = _cartProducts.asStateFlow()

    init {
        initializeUser()
        fetchAPIData()
    }

    private var _cartSize = MutableStateFlow(0)
    val cartSize: StateFlow<Int> = _cartSize.asStateFlow()

    private val _selectedProduct = MutableStateFlow<ProductModel?>(null)
    val selectedProduct: StateFlow<ProductModel?> = _selectedProduct

    private fun initializeUser() {
        val sharedPref = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("userEmail", null)
        viewModelScope.launch {
            userEmail?.let { email ->
                repository.getUserByEmail(email)?.let { user ->
                    _currentUser.value = user
                    _historyList.value = user.searchHistory
                    updateWishList(user.wishList)
                    updateCartList(user.cartList)

                    user.avatar?.let { avatar ->
                        if (avatar.isNotEmpty()) {
                            val bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.size)
                            _bitmap.value = bitmap
                        }
                    }
                    setSelectedCountry(user.country)
                }
            }
        }
    }

    fun fetchAPIData() {
        loadProducts()
        loadCategories()
    }

    fun setUserManagerStatus(isManager: Boolean) {
        _isUserManager.value = isManager
    }

    fun setSelectedProduct(product: ProductModel) {
        _selectedProduct.value = product
    }

    fun addToCart(product: ProductModel) {
        viewModelScope.launch {
            val currentCartList = _currentUser.value?.cartList ?: listOf()
            val newCartItemId = (currentCartList.maxByOrNull { it.id }?.id ?: 0) + 1
            val newCartItem = CartItemModel(id = newCartItemId, product = product, quantity = 1)
            val updatedCartList = currentCartList + newCartItem
            updateCartList(updatedCartList)
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            val currentCartList = _currentUser.value?.cartList ?: listOf()
            val updatedCartList = currentCartList.filterNot { it.product.id == productId }
            updateCartList(updatedCartList)
        }
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
            updateCountry(country)
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
            _currentUser.value?.let { currentUser ->
                val updatedWishList = currentUser.wishList.toMutableList().apply { add(product) }
                val updatedUser = currentUser.copy(wishList = updatedWishList)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _savedProducts.value = updatedWishList.toSet()
            }
        }
    }

    fun getProductsFromDB() {
        viewModelScope.launch {
            _currentUser.value?.wishList?.let {
                _savedProducts.value = it.toSet()
            }
        }
    }

    fun deleteProduct(product: ProductModel) {
        viewModelScope.launch {
            _currentUser.value?.let { currentUser ->
                val updatedWishList = currentUser.wishList.filterNot { it.id == product.id }
                val updatedUser = currentUser.copy(wishList = updatedWishList)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _savedProducts.value = updatedWishList.toSet()
            }
        }
    }

    //Auth VM logic
    private val _authStateState = MutableStateFlow<AuthState>(AuthState())
    val authState: StateFlow<AuthState> = _authStateState

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        isManager: Boolean
    ) {
        if (password != confirmPass) {
            _authStateState.value = AuthState(error = "Passwords do not match")
            return
        }
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            viewModelScope.launch {
                                val newUser = UserObject(
                                    id = task.result?.user?.uid.hashCode(),
                                    name = name,
                                    email = email,
                                    password = password,
                                    avatar = ByteArray(0),
                                    wishList = listOf(),
                                    cartList = listOf(),
                                    searchHistory = listOf(),
                                    country = "America",
                                    isManager = isManager,
                                )
                                repository.saveUser(newUser)
                                _authStateState.value = AuthState(success = true)
                            }
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
                            viewModelScope.launch {
                                repository.getUserByEmail(email)?.let { user ->
                                    saveUserEmail(email)
                                    _currentUser.value = user
                                    _authStateState.value = AuthState(success = true)
                                } ?: run {
                                    _authStateState.value =
                                        AuthState(error = "User not found in local database")
                                }
                            }
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

    fun logout() {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
            _currentUser.value = null
            clearUserEmail()
            _authStateState.value = AuthState(success = false)
        }
    }

    fun saveUserEmail(email: String) {
        val sharedPref =
            getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("userEmail", email).apply()
    }

    fun clearUserEmail() {
        val sharedPref =
            getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().remove("userEmail").apply()
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
                _selectedMinPrice.value =
                    productList.minByOrNull { it.price }?.price?.toFloat() ?: 0f
                _selectedMaxPrice.value =
                    productList.maxByOrNull { it.price }?.price?.toFloat() ?: 0f
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun loadProductsByCategoryId(id: Int) {
        viewModelScope.launch {
            try {
                val productList = networkService.getProductByCategory(id)
                _products.value = productList
                _allProducts.value = productList
                _minPrice.value = productList.minByOrNull { it.price }?.price?.toFloat() ?: 0f
                _maxPrice.value = productList.maxByOrNull { it.price }?.price?.toFloat() ?: 0f
                _selectedMinPrice.value =
                    productList.minByOrNull { it.price }?.price?.toFloat() ?: 0f
                _selectedMaxPrice.value =
                    productList.maxByOrNull { it.price }?.price?.toFloat() ?: 0f
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun updateProduct(id: Int, product: PostProductModel) {
        viewModelScope.launch {
            try {
                networkService.updateProduct(id, product)
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

    fun postNewCategory(category: PostCategoryModel) {
        viewModelScope.launch {
            try {
                networkService.createCategory(category)
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun updateCategory(id: Int, category: PostCategoryModel) {
        viewModelScope.launch {
            try {
                networkService.updateCategory(id, category)
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            try {
                networkService.deleteCategory(id)
            } catch (e: Exception) {

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
        viewModelScope.launch {
            val avatarBytes = bitmapToByteArray(bitmap)
            _currentUser.value?.let { user ->
                val updatedUser = user.copy(avatar = avatarBytes)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _bitmap.value = bitmap
            }
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            return toByteArray()
        }
    }

    var name by mutableStateOf("Dev p")
    var mail by mutableStateOf("dev@gmail.com")
    var password by mutableStateOf("1111")
    var isSheetOpen: Boolean by mutableStateOf(false)
    fun onNameChange(newName: String) {
        viewModelScope.launch {
            _currentUser.value?.let { currentUser ->
                val updatedUser = currentUser.copy(name = newName)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
            }
        }
    }

    fun updateUserEmail(newEmail: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            it.updateEmail(newEmail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        _currentUser.value?.let { currentUser ->
                            val updatedUser = currentUser.copy(email = newEmail)
                            repository.saveUser(updatedUser)
                            _currentUser.value = updatedUser
                        }
                    }
                } else {
                    _authStateState.value =
                        AuthState(error = task.exception?.message ?: "Failed to update email")
                }
            }
        }
    }

    fun updateUserPassword(newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            it.updatePassword(newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        _currentUser.value?.let { currentUser ->
                            val updatedUser = currentUser.copy(password = newPassword)
                            repository.saveUser(updatedUser)
                            _currentUser.value = updatedUser
                        }
                    }
                } else {
                    _authStateState.value =
                        AuthState(error = task.exception?.message ?: "Failed to update password")
                }
            }
        }
    }

    fun onIsSheetOpenChange(newBoolean: Boolean) {
        isSheetOpen = newBoolean
    }

    fun updateWishList(newWishList: List<ProductModel>) {
        viewModelScope.launch {
            _currentUser.value?.let { currentUser ->
                val updatedUser = currentUser.copy(wishList = newWishList)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _favoriteProducts.value = newWishList.map { it.id }.toSet()
            }
        }
    }

    fun updateCartList(newCartList: List<CartItemModel>) {
        viewModelScope.launch {
            _currentUser.value?.let { currentUser ->
                val updatedUser = currentUser.copy(cartList = newCartList)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _cartProducts.value = newCartList.map { it.product }
                _cartSize.value = newCartList.sumOf { it.quantity }
            }
        }
    }

    fun updateHistory(newSearchItem: String) {
        viewModelScope.launch {
            val currentUser = _currentUser.value
            currentUser?.let { user ->
                val updatedSearchHistory = user.searchHistory.toMutableList().apply {
                    add(newSearchItem)
                }
                val updatedUser = user.copy(searchHistory = updatedSearchHistory)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                _historyList.value = updatedSearchHistory
            }
        }
    }

    fun updateCountry(newCountry: String) {
        viewModelScope.launch {
            _currentUser.value?.let { currentUser ->
                val updatedUser = currentUser.copy(country = newCountry)
                repository.saveUser(updatedUser)
                _currentUser.value = updatedUser
                setSelectedCountry(newCountry)
            }
        }
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

