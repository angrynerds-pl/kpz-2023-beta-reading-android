package com.example.betareadingapp.feature_text.presentation.recentTexts

import androidx.compose.foundation.Image
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
import com.example.betareadingapp.feature_text.presentation.BottomBar
import com.example.betareadingapp.feature_text.presentation.mytexts.components.TextItem

@Composable
fun RecentTextsScreen(
    navController: NavController,
    viewModel: RecentTextsViewModel = hiltViewModel()
) {

    val allTexts = viewModel.allTexts.collectAsState()
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
                        "Recent texts",
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
                .background(Color(0xFF43928A))
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
                placeholder = { Text("Search") },
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

            if (allTexts.value.error.isNotEmpty())
                Text(allTexts.value.error)
            if (allTexts.value.isLoading) {
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
                items(allTexts.value.data ?: emptyList()) { text ->
                    TextItem(text, navController)
                }
            }

        }
    }
}