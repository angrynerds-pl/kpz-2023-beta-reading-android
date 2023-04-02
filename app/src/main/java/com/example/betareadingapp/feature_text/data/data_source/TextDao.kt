package com.example.betareadinapp.feature_text.data.data_source
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betareadingapp.feature_text.domain.model.Text
import kotlinx.coroutines.flow.Flow

@Dao
interface TextDao {

    @Query("SELECT * FROM text")
    fun getTexts(): Flow<List<Text>>

    @Query("SELECT * FROM text where id = :id")
    suspend fun getTextById(id: Int): Text?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertText(text: Text)

    @Delete
    suspend fun deleteText(text: Text)

}