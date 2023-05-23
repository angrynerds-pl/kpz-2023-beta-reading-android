package com.example.betareadingapp.di

import android.content.Context

import com.example.betareadingapp.domain.repository.TextRepository
import com.example.betareadingapp.domain.repository.UserRepository
import com.example.betareadingapp.domain.use_case.DeleteText
import com.example.betareadingapp.domain.use_case.FilterTexts
import com.example.betareadingapp.domain.use_case.GetTexts
import com.example.betareadingapp.domain.use_case.TextUseCases
import com.example.betareadingapp.domain.use_case.auth.AuthUseCases
import com.example.betareadingapp.domain.use_case.auth.LoginUser
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.example.betareadingapp.feature_text.domain.util.error.createDefaultHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TextModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideExceptionHandler(): ExceptionHandler = createDefaultHandler()

    @Provides
    @Singleton
    fun provideTextUseCases(repository: TextRepository): TextUseCases {
        return TextUseCases(
            getTexts = GetTexts(repository),
            deleteText = DeleteText(repository),
            filterTexts = FilterTexts()
        )
    }

}