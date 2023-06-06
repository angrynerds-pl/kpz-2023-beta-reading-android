package com.example.betareadingapp.presentation.profile

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.presentation.BottomBar
import com.example.betareadingapp.presentation.components.TopBarWithImage
import com.example.betareadingapp.presentation.utill.Screen
import java.time.format.TextStyle

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState = viewModel.profileState.collectAsState()
    val isLogout = viewModel.isLogout
    Scaffold(
        topBar = {
            TopBarWithImage(stringResource(R.string.profile))
        },
        bottomBar = {
            BottomBar(navController)
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(R.color.background_color))
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundImage(
                    image = painterResource(id = R.drawable.pudzian),
                    modifier = Modifier
                        .size(200.dp)
                        .weight(3f)
                )
                Row(
                    modifier = Modifier.weight(2f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("LogOut")
                    IconButton(onClick = {
                        viewModel.logOut()
                    }
                    ) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null
                        )
                    }

                }
                if (profileState.value.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(40.dp)
                    )

                }


            }
            Column(
                modifier = Modifier.padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    (profileState.value.user?.name ?: "") + " " + (profileState.value.user?.surname ?: ""),
                    fontSize = 22.sp,
                )
                Text(
                    profileState.value.user?.email ?: "",
                    fontSize = 22.sp, // szkoda ze dopiero teraz odkrylem MaterialTheme
                )
                if(profileState.value.error.isNotEmpty()){
                    Text(profileState.value.error)
                }
            }
        }

    }
    LaunchedEffect(isLogout.value) {
        if (isLogout.value) {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.LoginScreen.route) {
                    inclusive = true
                } // to do poprawki fireStoreDatabase.clearPersistence()
                // dodanie zapewne do repository, jest  to asynchroniczne
                // i tak problem route jest, ale to juz bardziej skomplikowane
            }
        }
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

