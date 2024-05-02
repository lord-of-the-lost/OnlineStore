package com.example.onlinestore.core

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.onlinestore.core.location.LocationUseCase
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.core.repository.StoreRepository
import com.example.onlinestore.core.storage.AppDatabase
import com.example.onlinestore.core.storage.ProductDAO
import com.example.onlinestore.core.storage.UserDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreViewModel(application: Application) : AndroidViewModel(application) {

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

    private val repository: StoreRepository by lazy {
        StoreRepository(productDAO, userDAO)
    }

    private val locationUseCase: LocationUseCase by lazy {
        LocationUseCase(application)
    }

    var savedProducts: MutableState<List<ProductModel>?> = mutableStateOf(null)
    var currentCountry: StateFlow<String> = locationUseCase.countryStateFlow


    // Location VM logic
    fun requestLocationUpdates() {
        locationUseCase.requestLocationUpdates(this)
    }

    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    //Storage Product VM logic
    fun saveProduct(product: ProductModel) {
        viewModelScope.launch {
            repository.saveProduct(product)
        }
    }

    fun getProductsFromDB() {
        viewModelScope.launch {
            savedProducts.value = repository.getAllProducts()
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
}

data class AuthState(
    val success: Boolean = false,
    val error: String = ""
)
