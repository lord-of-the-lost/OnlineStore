package com.example.onlinestore.views.AuthentificationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel: ViewModel() {

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
