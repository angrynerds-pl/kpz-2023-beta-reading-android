package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecentTexts
@Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
){

    operator fun invoke(filterText : String) : Flow<Resource<List<Text>>>  = flow{
        emit(Resource.Loading())

       val recentTexts = repository.getRecentTexts(filterText)

        emit(Resource.Success(recentTexts))

    }.catch(exceptionHandler::handle)
}