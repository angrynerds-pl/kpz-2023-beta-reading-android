package com.example.betareadingapp.presentation.comments

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.betareadingapp.domain.model.Comment
import com.example.betareadingapp.domain.use_case.log_user.GetComments
import com.example.betareadingapp.feature_text.data.repository.CommentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import androidx.paging.cachedIn
import com.example.betareadingapp.domain.model.CommentUploadData
import com.example.betareadingapp.domain.use_case.log_user.LogUserUseCases
import com.example.betareadingapp.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CommentsViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getComments: GetComments,
    private val logUserUseCases: LogUserUseCases
) : ViewModel() {

    private lateinit var currentTextId: String

    var commentsFlow: Flow<PagingData<Comment>> = emptyFlow()

    private val _addComment = mutableStateOf("")

    val addComment: State<String> = _addComment

    fun setaddComment(newComment: String) {
        _addComment.value = newComment
    }

    init {
        savedStateHandle.get<String>("textId")?.let { textId ->
            this.currentTextId = textId
            try {
                val commentPagingSource = CommentPagingSource(currentTextId)
                commentsFlow = getComments(commentPagingSource).cachedIn(viewModelScope)
            } catch (e: Exception) {
                Log.e("commentviewmodelinit", "Error", e)
            }
        }
    }

    fun addComment() {

        logUserUseCases.addComment(
            CommentUploadData(currentTextId, addComment.value)
        ).onEach {
            when(it) {
                is Resource.Success ->{
                    _addComment.value = ""

                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

}