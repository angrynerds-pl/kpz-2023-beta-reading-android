package com.example.betareadingapp.feature_text.presentation.mytexts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.feature_text.domain.util.Resource
import com.example.betareadingapp.feature_text.domain.util.networkState.CommentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {

    private val _Comments = MutableStateFlow(CommentState())

    val comments: StateFlow<CommentState> = _Comments


    val _addComment = mutableStateOf("")
    val addComment: State<String> = _addComment


    fun addComment(setString: String) {
        _addComment.value = setString
    }
    init {
        getComments()
    }

    fun getComments() {

        val textId = "Dhz4kcunDHIAFjhl5pvu"
        authRepository.getComments(textId).onEach {

            when (it) {
                is Resource.Loading -> {
                    _Comments.value = CommentState(isLoading = true)
                }
                is Resource.Error -> {
                    _Comments.value = CommentState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    _Comments.value = CommentState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }


}
