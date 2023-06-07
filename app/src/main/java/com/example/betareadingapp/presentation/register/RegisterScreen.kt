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
    val userState = viewModel.authState.collectAsState()
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
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(55.dp)
                        .clickable { navController.navigate(Screen.LoginScreen.route) }

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.register_account),
                fontSize = 25.sp,
                color = MaterialTheme.colors.onPrimary,
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
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                TextFieldWithText(
                    textvalue = stringResource(R.string.name),
                    value = viewModel.name.value,
                    onValueChange = { viewModel.onNameChanged(it) },
                    placeholder = stringResource(R.string.name)
                )
                TextFieldWithText(
                    textvalue = stringResource(R.string.surname),
                    value = viewModel.surname.value,
                    onValueChange = { viewModel.onSurnameChanged(it) },
                    placeholder = stringResource(R.string.surname)
                )
                TextFieldWithText(
                    textvalue = stringResource(R.string.email),
                    value = viewModel.email.value,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    placeholder = stringResource(R.string.email)
                )
                TextFieldWithPassword(
                    textvalue = stringResource(R.string.password),
                    value = viewModel.password.value,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = stringResource(R.string.password)
                )
                TextFieldWithPassword(
                    textvalue = stringResource(R.string.repeat_password),
                    value = viewModel.repeatPassword.value,
                    onValueChange = { viewModel.onRepeatPasswordChanged(it) },
                    placeholder = stringResource(R.string.repeat_password)
                )
            }

            Button(
                onClick = {
                    viewModel.register()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.4f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50.dp)),

                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_orange)),
                content = {
                    Text(
                        text = stringResource(R.string.register),
                        color = MaterialTheme.colors.onPrimary
                    )
                },
            )

            if (userState.value.error.isNotEmpty())
                Text(
                    text = userState.value.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    color = MaterialTheme.colors.onPrimary
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

@Composable
fun CustomPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,

    ) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(RoundedCornerShape(50.dp)),
        placeholder = { Text(placeholder) },
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

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,

    ) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(RoundedCornerShape(50.dp)),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.surface,
            unfocusedBorderColor = MaterialTheme.colors.surface,
            backgroundColor = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onSurface,
            placeholderColor = MaterialTheme.colors.onSurface
        ),
    )
}

@Composable
fun TextFieldWithText(
    textvalue: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    Text(
        text = textvalue,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(bottom = 5.dp),
        color = Color.White
    )
    CustomTextField(value, onValueChange, placeholder)
    Spacer(modifier = Modifier.height(8.dp))

}

@Composable
fun TextFieldWithPassword(
    textvalue: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    Text(
        text = textvalue,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(bottom = 5.dp),
        color = Color.White
    )
    CustomPasswordField(value, onValueChange, placeholder)
    Spacer(modifier = Modifier.height(8.dp))

}

