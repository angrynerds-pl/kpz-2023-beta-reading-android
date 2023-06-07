package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.feature_text.data.repository.Repository
import javax.inject.Inject

class logOut
@Inject constructor(
    private val repository: Repository
){
    operator fun invoke(){
        repository.logOut()
    }
}