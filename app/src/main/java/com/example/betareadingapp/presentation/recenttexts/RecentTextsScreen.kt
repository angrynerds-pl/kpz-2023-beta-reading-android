package com.example.betareadingapp.presentation.recenttexts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.presentation.BottomBar
import com.example.betareadingapp.presentation.components.TopBarWithImage
import com.example.betareadingapp.presentation.mytexts.components.TextItem
import com.example.betareadingapp.presentation.utill.Screen
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun RecentTextsScreen(
    navController: NavController,
    viewModel: RecentTextsViewModel = hiltViewModel()
) {
    val recentTexts = viewModel.recentTextsState.collectAsState()
    Scaffold(
        topBar = {
            TopBarWithImage(stringResource(R.string.recent_texts))
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
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = viewModel.search.value,
                onValueChange = { viewModel.setSearch(it) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(50.dp)),
                placeholder = { Text(stringResource(R.string.search)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    backgroundColor = Color.White
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                }
            )
            Spacer(modifier = Modifier.height(25.dp))

            if (recentTexts.value.error.isNotEmpty())
                Text(recentTexts.value.error)
            if (recentTexts.value.isLoading) {
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
                items(recentTexts.value.data ?: emptyList()) { text ->
                    TextItem(text, {
                        viewModel.downloadPdf(text.file)   // TODO()
                    }, {
                        navController.navigate(
                            Screen.CommentsScreen.route +
                                    "?textId=${text.textId}"
                        )
                    })
                }
            }

        }
    }
}
