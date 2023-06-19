package com.example.betareadingapp.presentation.recenttexts

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.presentation.BottomBar
import com.example.betareadingapp.presentation.components.TopBarWithImage
import com.example.betareadingapp.presentation.mytexts.components.TextItem
import com.example.betareadingapp.presentation.utill.Screen

@Composable
fun RecentTextsScreen(
    navController: NavController,
    viewModel: RecentTextsViewModel = hiltViewModel()
) {
    val recentTexts = viewModel.recentTextsState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val downloadedPdfUri = remember {
        viewModel.pdfUriState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }.collectAsState(null).value

    if (downloadedPdfUri != null) {
        (LocalContext.current as Activity).startActivity(
            Intent(Intent.ACTION_VIEW)
                .also {
                    it.setDataAndType(downloadedPdfUri, "application/pdf")
                    it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                })
    }

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
                    focusedBorderColor = MaterialTheme.colors.surface,
                    unfocusedBorderColor = MaterialTheme.colors.surface,
                    backgroundColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSurface
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            )
            Spacer(modifier = Modifier.height(25.dp))

            if (recentTexts.value.error.isNotEmpty())
                Text(
                    recentTexts.value.error,
                    color = MaterialTheme.colors.onPrimary
                )
            if (recentTexts.value.isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary
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
