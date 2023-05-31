package com.example.betareadingapp.feature_text.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.betareadingapp.domain.model.Comment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CommentPagingSource(
    private val textId: String,
) : PagingSource<DocumentSnapshot, Comment>() {
    private var fireStoreDatabase = FirebaseFirestore.getInstance()

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, Comment> {
        return try {
            // Pobierz pierwsze 10 lub następne 10 komentarzy na podstawie parametrów
            val currentPage = params.key?.let {
                fireStoreDatabase
                    .collection("Comments")
                    .whereEqualTo("textId", textId)
                    .startAfter(it)
                    .limit(10L)
                    .get()
                    .await()
            } ?: fireStoreDatabase
                .collection("Comments")
                .whereEqualTo("textId", textId)
                .limit(10L)
                .get()
                .await()

            val comments = currentPage.documents.mapNotNull { documentToComment(it) }

            val lastVisible = currentPage.documents.lastOrNull()

            return LoadResult.Page(
                data = comments,
                prevKey = null, // biblioteka Paginacja nie obsługuje domyślnie paginacji wstecznej
                nextKey = lastVisible // jako klucz dla następnego wywołania `load()`
            )
        }catch (e: Exception) {
            Log.e("CommentPagingSource", "Error loading comments", e)
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Comment>): DocumentSnapshot? {
        return null
    }
    fun documentToComment(doc: DocumentSnapshot): Comment? {
        val textId = doc.getString("textId") ?: return null
        val userId = doc.getString("userId") ?: return null
        val commentId = doc.getString("commentId") ?: return null
        val timestamp = doc.getTimestamp("timestamp") ?: return null
        val content = doc.getString("content") ?: return null
        val author = doc.getString("author") ?: return null

        return Comment(textId, userId, commentId, timestamp, content, author)
    }
}
