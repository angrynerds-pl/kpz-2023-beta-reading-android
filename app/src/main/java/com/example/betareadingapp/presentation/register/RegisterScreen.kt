package com.example.betareadingapp.presentation.register

import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.betareadingapp.R
import com.example.betareadingapp.presentation.utill.Screen

@Composable

fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val userState = viewModel.user.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(55.dp)
                        .clickable { navController.navigate(Screen.LoginScreen.route) }

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.register_account),
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Spacer(modifier = Modifier.height(if (userState.value.isLoading) 0.dp else 40.dp))

            if (userState.value.isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.name),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    color = Color.White
                )
                OutlinedTextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.onNameChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(50.dp)),
                    placeholder = { Text(stringResource(R.string.name)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.surname),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    color = Color.White
                )
                OutlinedTextField(
                    value = viewModel.surname.value,
                    onValueChange = { viewModel.onSurnameChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(50.dp)),
                    placeholder = { Text(stringResource(R.string.surname)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.email),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    color = Color.White
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
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.password),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    color = Color.White
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
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.repeat_password),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    color = Color.White
                )
                OutlinedTextField(
                    value = viewModel.repeatPassword.value,
                    onValueChange = { viewModel.onRepeatPasswordChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(50.dp)),
                    placeholder = { Text(stringResource(R.string.repeat_password)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )
            }

            Button(
                onClick = {
                    viewModel.register()
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(0.4f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50.dp)),

                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_orange)),
                content = {
                    Text(
                        text = stringResource(R.string.register),
                        color = Color.White
                    )
                },
            )

            if (!userState.value.error.isEmpty())
                Text(
                    text = userState.value.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    color = Color.White
                )

        }

        LaunchedEffect(userState.value.data) {
            if (userState.value.data != null) {       // przejscie do logowania TODO
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.RegisterScreen.route) { inclusive = true }
                }
            }
        }
    }

}

