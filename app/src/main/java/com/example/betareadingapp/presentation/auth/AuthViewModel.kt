package com.example.betareadingapp.presentation.auth

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.use_case.auth.AuthData
import com.example.betareadingapp.domain.use_case.auth.AuthUseCases
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private var authUseCases: AuthUseCases,
) : ViewModel() {

    private val _user = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _user

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        authUseCases.loginUser(AuthData(email.value, password.value))
            .onEach {
                _user.value = when (it) {
                    is Resource.Loading -> AuthState(isLoading = true)
                    is Resource.Error -> AuthState(error = it.message ?: "")
                    is Resource.Success -> AuthState(data = it.data)
                }
            }
            .launchIn(viewModelScope)
    }
}