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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.feature_text.presentation.BottomBar
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
            TopAppBar(
                backgroundColor = Color(0xFF43928A),
                contentPadding = PaddingValues(10.dp),
                elevation = 0.dp
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                ) {
                    Text(
                        "Add text",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.smalllogo),
                        contentDescription = "Small Logo",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.CenterVertically)
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
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
            ) {
                Text(
                    "Title",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 6.dp, start = 6.dp)
                )
                OutlinedTextField(
                    value = viewModel.titleField.value,
                    onValueChange = { viewModel.setTitleField(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(30.dp)),
                    placeholder = { Text("Title") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        backgroundColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                "Add text",
                color = Color.White,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = viewModel.content.value,
                onValueChange = { viewModel.setContent(it) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.5f)
                    .clip(RoundedCornerShape(10.dp)),
                placeholder = { Text("Text") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    backgroundColor = Color.White
                )
            )
            Text(
                "and",
                color = Color.White,
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
                    "Attach File",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Sharp.AttachFile, contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 5.dp),
                    tint = Color.White
                )

            }
            Button(
                onClick = {
                    viewModel.uploadPdfWithText()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.4f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFE902A)),
                content = {
                    Text(
                        text = "Submit",
                        color = Color.White
                    )
                },
            )
            Text(
                text = pdfState.value.error,
                textAlign = TextAlign.Center,
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
                        color = Color.White
                    )
                }
            }
        }
    }
}
