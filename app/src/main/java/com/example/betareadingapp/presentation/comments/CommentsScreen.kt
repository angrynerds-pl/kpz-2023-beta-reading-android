package com.example.betareadingapp.presentation.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.presentation.BottomBar
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.betareadingapp.presentation.comments.components.CommentItem

@Composable
fun CommentsScreen(
    navController: NavController,
    viewModel: CommentsViewModel = hiltViewModel()
) {
    val lazyPagingItems = viewModel.commentsFlow.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar() {

            }
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(R.color.background_color))
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                items(count = lazyPagingItems.itemCount) { index ->
                    val comment = lazyPagingItems[index]
                    if (comment != null) {
                        // Wyświetl konkretny atrybut z obiektu Comment
                        CommentItem(navController, comment)
                    }
                }
            }
            OutlinedTextField(
                value = viewModel.addComment.value,
                onValueChange = { viewModel.setaddComment(it) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(top = 10.dp, bottom = 10.dp),
                placeholder = { Text(stringResource(R.string.add_coment)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.surface,
                    unfocusedBorderColor = MaterialTheme.colors.surface,
                    backgroundColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSurface
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.addComment() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            )
        }

    }
}