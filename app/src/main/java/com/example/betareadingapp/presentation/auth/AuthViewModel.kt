package com.example.betareadingapp.presentation.auth

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.model.LoginData
import com.example.betareadingapp.domain.use_case.auth.AuthUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

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
        authUseCases.loginUser(LoginData(email.value, password.value))
            .onEach {
                _authState.value = when (it) {
                    is Resource.Loading -> AuthState(isLoading = true)
                    is Resource.Error -> AuthState(error = it.message ?: "")
                    is Resource.Success -> AuthState(data = it.data)
                }
            }
            .launchIn(viewModelScope)
    }
}