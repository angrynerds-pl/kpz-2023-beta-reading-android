package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.domain.use_case.FilterTexts
import javax.inject.Inject

class LogUserUseCases
@Inject constructor(
    val getMyTexts: GetMyTexts,
    val getFileNameFromUri: GetFileNameFromUri,
    val uploadNotePdf: UploadNotePdf,
    val addComment : AddComment,
    val getRecentTexts: GetRecentTexts,
    val filterTexts: FilterTexts,
    val downLoadPdf: DownLoadPdf,
    val getUserData: GetUserData,
    val logOut: logOut
)