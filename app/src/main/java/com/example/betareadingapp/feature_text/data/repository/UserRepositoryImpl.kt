package com.example.betareadingapp.feature_text.data.repository

import com.example.betareadingapp.domain.model.User
import com.example.betareadingapp.domain.repository.UserRepository
import com.example.betareadingapp.domain.util.networkState.AuthState
import com.example.betareadingapp.domain.util.networkState.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor()
 : UserRepository {

    private val _userState = MutableStateFlow(UserState())

    override val userState : StateFlow<UserState> = _userState
    override fun setUserState(userState: UserState) {
        _userState.value = userState
    }

    private val _userData = MutableStateFlow(User())

    override val userData :StateFlow<User> = _userData

    override fun setUserData(userData: User) {
        _userData.value = userData
    }

}