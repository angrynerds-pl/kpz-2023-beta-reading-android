package com.example.betareadingapp.di

import android.app.Application
import androidx.room.Room
import com.example.betareadingapp.feature_text.data.data_source.TextDatabase
import com.example.betareadingapp.feature_text.data.repository.TextRepositoryImpl
import com.example.betareadingapp.feature_text.domain.repository.TextRepository
import com.example.betareadingapp.feature_text.domain.use_case.DeleteText
import com.example.betareadingapp.feature_text.domain.use_case.FilterTexts
import com.example.betareadingapp.feature_text.domain.use_case.GetTexts
import com.example.betareadingapp.feature_text.domain.use_case.TextUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TextModule {
    @Provides
    @Singleton
    fun provideTextDatabase(app: Application): TextDatabase{
        return Room.databaseBuilder(
            app,
            TextDatabase::class.java,
            TextDatabase.DATABASE_NAME
        ).build()
    }



    @Provides
    @Singleton
    fun provideTextRepository(db: TextDatabase): TextRepository{
        return TextRepositoryImpl(db.textDao)
    }

    @Provides
    @Singleton
    fun provideTextUseCases(repository: TextRepository): TextUseCases{
        return TextUseCases(
            getTexts = GetTexts(repository),
            deleteText = DeleteText(repository),
            filterTexts = FilterTexts()
        )
    }
}