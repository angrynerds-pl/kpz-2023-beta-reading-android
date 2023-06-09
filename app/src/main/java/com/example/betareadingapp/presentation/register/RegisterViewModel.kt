package com.example.betareadingapp.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.model.RegisterData
import com.example.betareadingapp.domain.use_case.auth.AuthUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _repeatPassword = mutableStateOf("")
    val repeatPassword: State<String> = _repeatPassword

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _surname = mutableStateOf("")
    val surname: State<String> = _surname

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onRepeatPasswordChanged(newPassword: String) {
        _repeatPassword.value = newPassword
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onSurnameChanged(newSurname: String) {
        _surname.value = newSurname
    }

    fun register() {
        val registerData = RegisterData(
            name.value,
            surname.value,
            email.value,
            password.value,
            repeatPassword.value
        )
        authUseCases.registerUser(registerData).onEach {
            _authState.value = when (it) {
                is Resource.Loading -> AuthState(isLoading = true)
                is Resource.Error -> AuthState(error = it.message ?: "")
                is Resource.Success -> AuthState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }

}