package com.gideondev.echotextapp.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gideondev.echotextapp.viewmodel.EchoTextViewModel
import com.gideondev.echotextapp.R
import com.gideondev.echotextapp.ui.theme.EchoTextAppTheme
import com.gideondev.echotextapp.ui.theme.darkBlue

@Composable
fun MainScreen(
    viewModel: EchoTextViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    InputScreen(
        submitText = { value ->
            viewModel.echoText(value)
        },
        state = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputScreen(
    submitText: (inputtedValue: String) -> Unit,
    state: EchoTextViewModel.UiState,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(R.string.app_name), color = Color.White,
                        fontSize = 18.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkBlue,
                    titleContentColor = Color.White,
                )
            )
        },

        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                var text by remember { mutableStateOf("") }

                Text(
                    stringResource(R.string.txt_welcome),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    style = TextStyle(fontSize = 16.sp)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = text,
                    enabled = true,
                    onValueChange = { newText ->
                        text = newText
                    },
                    placeholder = { Text(stringResource(R.string.txt_place_holder)) },
                    shape = RoundedCornerShape(16.dp),
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    content = {
                        Text(
                            text = stringResource(R.string.txt_submit), color = Color.White,
                            fontSize = 18.sp
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = darkBlue),
                    onClick = { submitText(text) }
                )

                Text(
                    state.inputtedText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    )
}

@Preview
@Composable
fun InputScreenPreview() {
    EchoTextAppTheme {
        InputScreen(
            submitText = {},
            EchoTextViewModel.UiState()
        )
    }
}