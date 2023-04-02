package com.example.betareadingapp.feature_text.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.betareadinapp.feature_text.data.data_source.TextDao
import com.example.betareadingapp.feature_text.domain.model.Text

@Database(
    entities = [Text::class],
    version = 1
)
abstract class TextDatabase: RoomDatabase() {
    abstract val textDao: TextDao
}