package com.example.betareadingapp.domain.repository

import com.example.betareadingapp.domain.model.User
import com.example.betareadingapp.domain.util.networkState.AuthState
import com.example.betareadingapp.domain.util.networkState.UserState
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    val userState : StateFlow<UserState>
    fun setUserState(userState : UserState)

    val userData : StateFlow<User>
    fun setUserData(userData : User)


}