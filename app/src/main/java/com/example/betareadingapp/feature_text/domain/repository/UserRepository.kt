package com.example.betareadingapp.feature_text.domain.repository

import com.example.betareadingapp.feature_text.domain.model.User
import com.example.betareadingapp.feature_text.domain.util.networkState.AuthState
import com.example.betareadingapp.feature_text.domain.util.networkState.UserState
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    val userState : StateFlow<UserState>
    fun setUserState(userState : UserState)

    val userData : StateFlow<User>
    fun setUserData(userData : User)


}