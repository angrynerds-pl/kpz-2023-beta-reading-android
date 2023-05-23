package com.example.betareadingapp.feature_text.presentation.mytexts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.domain.model.Comment
import com.example.betareadingapp.feature_text.presentation.BottomBar

@Composable
fun CommentsScreen(
    navController: NavController,
    viewModel: CommentsViewModel = hiltViewModel()
) {

    val comments = viewModel.comments.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF43928A),
                contentPadding = PaddingValues(10.dp),
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Comments",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.smalllogo),
                        contentDescription = "Small Logo",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF43928A))
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier.fillMaxWidth() // Okienko do dodawania komentarza na szerokość ekranu
            ) {
                OutlinedTextField(
                    value = viewModel.addComment.value,
                    onValueChange = { viewModel.addComment(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth() // Okienko do dodawania komentarza na szerokość ekranu
                        .clip(RoundedCornerShape(50.dp)),
                    placeholder = { Text("Add comment") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send Icon"
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            if (comments.value.error.isNotEmpty())
                Comment(comments.value.error)
            if (comments.value.isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(comments.value.data ?: emptyList()) { comment ->
                    Comment(comment)
                }
            }
        }
    }
}