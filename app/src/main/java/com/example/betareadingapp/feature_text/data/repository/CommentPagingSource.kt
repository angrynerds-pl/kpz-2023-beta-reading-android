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

            val comments = currentPage.documents.mapNotNull { it.toObject(Comment::class.java) }

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
}
