package com.example.betareadingapp.feature_text.presentation.auth

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.feature_text.data.repository.UserRepositoryImpl
import com.example.betareadingapp.feature_text.domain.model.User
import com.example.betareadingapp.feature_text.domain.repository.UserRepository
import com.example.betareadingapp.feature_text.domain.util.Resource
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.example.betareadingapp.feature_text.domain.util.networkState.AuthState
import com.example.betareadingapp.feature_text.domain.util.networkState.UserState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private var authRepository: AuthRepository,
) : ViewModel() {

    private val _user = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _user


    private val _userData = MutableStateFlow(UserState())
    val userData: StateFlow<UserState> = _userData


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


    fun login(email: String, password: String) {
        authRepository.login(email, password)
            .onEach {
            _user.value = when (it) {
                is Resource.Loading -> AuthState(isLoading = true)
                is Resource.Error -> AuthState(error = it.message ?: "")
                is Resource.Success -> AuthState(data = it.data)
            }
        }
            .launchIn(viewModelScope)
    }


    fun loggedUser() {

        authRepository.getLoggedUser().onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = AuthState(isLoading = true)
                }

                is Resource.Error -> {
                    _user.value = AuthState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    _user.value = AuthState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUserData() {
        authRepository.getUserData().onEach {
            when (it) {
                is Resource.Loading -> {
                    _userData.value = UserState(isLoading = true)
                }

                is Resource.Error -> {
                    _userData.value = UserState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    _userData.value = UserState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }


}