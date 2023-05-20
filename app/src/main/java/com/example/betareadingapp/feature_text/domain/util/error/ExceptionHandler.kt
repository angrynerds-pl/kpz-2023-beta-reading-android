package com.example.betareadingapp.feature_text.domain.util.error

import com.example.betareadingapp.feature_text.domain.util.Resource
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.flow.FlowCollector
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExceptionHandler @Inject constructor() {
    suspend fun <T> handleException(flowCollector: FlowCollector<Resource<T>>, exception: Throwable) =
        when(exception) {
            is HttpException ->   Resource.Error<T>(message = exception.localizedMessage ?: "Unknown Error")
            is IOException -> Resource.Error<T>(message = exception.localizedMessage ?: "Check Your Internet Connection")
            is java.lang.Exception -> Resource.Error<T>(message = exception.message.orEmpty())
            else -> throw exception
        }.also { flowCollector.emit(it) }
}
