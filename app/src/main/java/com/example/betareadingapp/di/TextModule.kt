package com.example.betareadingapp.di

import android.content.Context

import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.example.betareadingapp.feature_text.domain.util.error.createDefaultHandler
import com.google.firebase.firestore.FirebaseFirestore

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


}