package com.example.onlinestore.views.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    viewModel: StoreViewModel
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        containerColor = Color.White
    ) {
        var name by remember {
            mutableStateOf(viewModel.name)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = viewModel.name,
                onValueChanged = { viewModel.onNameChange(it) },
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )
            Text(
                text = if (viewModel.name.length == 0) {
                    "Имя не может быть пустым"
                } else {
                    ""
                },
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(Modifier.height(16.dp))

            TextField(
                value = viewModel.mail,
                onValueChanged = { viewModel.onMailChange(it) },
                maxLines = 1,
                keyboardType = KeyboardType.Email
            )
            Text(
                text = if (viewModel.mail.length == 0) {
                    "Почта не может быть пустой"
                } else {
                    ""
                },
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(Modifier.height(16.dp))

            TextField(
                value = viewModel.password,
                onValueChanged = { viewModel.onPasswordChange(it) },
                maxLines = 1,
                keyboardType = KeyboardType.Email
            )
            Text(
                text = if (viewModel.password.length == 0) {
                    "Пароль не может быть пустым"
                } else {
                    ""
                },
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(Modifier.height(25.dp))

            OutlinedButton(
                onClick = { onDismissRequest() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, color = colorResource(id = R.color.Green_Sheen))
            ) {
                Text(
                    text = "Enter",
                    color = colorResource(id = R.color.Green_Sheen)
                )
            }
        }
    }
}

@Composable
fun TextField(
    value: String,
    onValueChanged: (String) -> Unit,
    maxLines: Int,
    keyboardType: KeyboardType,
) {
    OutlinedTextField(
        maxLines = maxLines,
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = colorResource(id = R.color.Grey),
            focusedBorderColor = colorResource(id = R.color.Green_Sheen),
            unfocusedBorderColor = colorResource(id = R.color.Green_Sheen),
            cursorColor = colorResource(id = R.color.Green_Sheen),
            focusedLabelColor = colorResource(id = R.color.Green_Sheen),
            unfocusedLabelColor = colorResource(id = R.color.Green_Sheen)
        )
    )
}