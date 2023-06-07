package com.example.betareadingapp.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.betareadingapp.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betareadingapp.domain.model.User
import com.example.betareadingapp.domain.util.networkState.AuthState
import com.example.betareadingapp.presentation.utill.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val userState = viewModel.authState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.angryreaders),
                contentDescription = "Logo",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(if (userState.value.isLoading) 0.dp else 40.dp))

            if (userState.value.isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = viewModel.email.value,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(50.dp)),
                    placeholder = { Text(stringResource(R.string.email)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.surface,
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        backgroundColor = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onSurface,
                        placeholderColor = MaterialTheme.colors.onSurface
                    )
                )

            }

            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.password),
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = viewModel.password.value,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(50.dp)),
                    placeholder = { Text(stringResource(R.string.password)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.surface,
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        backgroundColor = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onSurface,
                        placeholderColor = MaterialTheme.colors.onSurface
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )
            }

            Row(modifier = Modifier.padding(top = 24.dp)) {
                Text("don't have an account? ")
                Text("sign up",
                    color = Color(0xFFFE902A),
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.RegisterScreen.route) })
            }

            Text(
                stringResource(R.string.forgot_your_password),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
//                            navController.navigate()   moze kiedys ^^
                    },
                textAlign = TextAlign.Center
            )



            Button(
                onClick = {
                    viewModel.login()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.4f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50.dp)),

                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_orange)),
                content = {
                    Text(
                        text = stringResource(R.string.login),
                        color = MaterialTheme.colors.onPrimary
                    )
                },
            )
            if (!userState.value.error.isEmpty())
                Text(
                    text = userState.value.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    color = MaterialTheme.colors.onPrimary
                )
        }

        LaunchedEffect(userState.value.data) {
            if (userState.value.data != null) {
                navController.navigate(Screen.MyTextsScreen.route) {
                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                }
            }
        }
    }
}