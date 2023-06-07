package com.example.betareadingapp.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.use_case.log_user.LogUserUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val logUserUseCases: LogUserUseCases
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    private val _isLogout = mutableStateOf(false)
    val isLogout : State<Boolean> = _isLogout

    init {
        getUserData()
    }


    fun logOut(){
        logUserUseCases.logOut()
        _isLogout.value = true
    }

    fun getUserData() {

        logUserUseCases.getUserData().onEach {

            _profileState.value = when (it) {
                is Resource.Loading -> ProfileState(isLoading = true)
                is Resource.Success -> ProfileState(user = it.data)
                is Resource.Error -> ProfileState(error = it.message ?: "Unknown error")
            }

        }.launchIn(viewModelScope)

    }

}