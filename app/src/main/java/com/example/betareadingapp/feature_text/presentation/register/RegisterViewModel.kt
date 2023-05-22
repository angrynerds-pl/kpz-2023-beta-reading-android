package com.example.betareadingapp.feature_text.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.feature_text.domain.model.User
import com.example.betareadingapp.feature_text.domain.util.Resource
import com.example.betareadingapp.feature_text.domain.util.networkState.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _user


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
        if (name.value.isEmpty() || surname.value.isEmpty()) {
            _user.value = AuthState(error = "Empty name or surname")
            return
        }
        if (password.value.isEmpty() || repeatPassword.value.isEmpty()) {
            _user.value = AuthState(error = "Given String is empty or null")
            return
        }
        if (password.value != repeatPassword.value) {
            _user.value = AuthState(error = "Different passwords")
            return

        }
        val user = User(name.value, surname.value, email.value)
        authRepository.register(email.value, password.value, user).onEach {
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

}