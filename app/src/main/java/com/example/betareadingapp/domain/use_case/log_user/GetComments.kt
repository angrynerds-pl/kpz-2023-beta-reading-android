package com.example.betareadingapp.domain.use_case.log_user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.betareadingapp.domain.model.Comment
import com.example.betareadingapp.feature_text.data.repository.CommentPagingSource
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetComments
@Inject constructor(
) {
    operator fun invoke(commentPagingSource: CommentPagingSource): Flow<PagingData<Comment>> {

        return Pager(PagingConfig(pageSize = 10)) {
            commentPagingSource
        }.flow
    }

}