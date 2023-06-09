package com.example.betareadingapp.feature_text.presentation.attachfile


import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.AttachFile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.presentation.BottomBar
import com.example.betareadingapp.presentation.components.TopBarWithImage
import kotlinx.coroutines.flow.collect


@Composable
fun AttachFileScreen(
    navController: NavController,
    viewModel: AttachFileViewModel = hiltViewModel(),
) {
    val pdfState = viewModel.pdfState.collectAsState()
    val pdfLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.setUriAndFilename(it)
        }
    }

    Scaffold(
        topBar = {
            TopBarWithImage(stringResource(R.string.add_text))
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
            Column(
            ) {
                Text(
                    stringResource(R.string.title),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 6.dp),
                )
                OutlinedTextField(
                    value = viewModel.titleField.value,
                    onValueChange = { viewModel.setTitleField(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(30.dp)),
                    placeholder = { Text(stringResource(R.string.title)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.surface,
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        backgroundColor = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onSurface,
                        placeholderColor = MaterialTheme.colors.onSurface
                    )
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                stringResource(R.string.add_text),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = viewModel.content.value,
                onValueChange = { viewModel.setContent(it) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.5f)
                    .clip(RoundedCornerShape(10.dp)),
                placeholder = { Text(stringResource(R.string.add_text)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.surface,
                    unfocusedBorderColor = MaterialTheme.colors.surface,
                    backgroundColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSurface
                )
            )
            Text(
                stringResource(R.string.and),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .clickable {
                        pdfLauncher.launch("application/pdf")
                    }
            ) {
                Text(
                    stringResource(R.string.attach_file),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = MaterialTheme.colors.onPrimary
                )
                Icon(
                    imageVector = Icons.Sharp.AttachFile, contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 5.dp),
                    tint = MaterialTheme.colors.onPrimary
                )

            }
            Text(
                text = viewModel.filename.value,
                color = MaterialTheme.colors.onPrimary
            )
            Button(
                onClick = {
                    viewModel.uploadNotePdf()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.4f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_orange)),
                content = {
                    Text(
                        text = stringResource(R.string.submit),
                        color = MaterialTheme.colors.onPrimary
                    )
                },
            )
            Text(
                text = pdfState.value.error,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(if (pdfState.value.isLoading) 0.dp else 40.dp))

            if (pdfState.value.isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

        }
    }
}
