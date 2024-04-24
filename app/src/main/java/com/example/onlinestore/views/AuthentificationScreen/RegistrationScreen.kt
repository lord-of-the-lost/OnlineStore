package com.example.onlinestore.views.AuthentificationScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinestore.R
import com.example.onlinestore.navigation.Screen

@Composable
fun RegistrationScreen(controller: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        var firstName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPass by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Complete your account",
                fontSize = 30.sp,
                color = colorResource(R.color.Dark_Arsenic),
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.padding(bottom = 40.dp))
            CustomTextField(
                "First Name",
                firstName,
                onChangeText = { firstName = it },
                "Enter your First name",
                "",
                false
            )
            CustomTextField(
                "E-mail",
                email,
                onChangeText = { email = it },
                "Enter your email",
                "",
                false
            )
            CustomTextField(
                "Password",
                password,
                onChangeText = { password = it },
                "Enter your password",
                "",
                false
            )
            CustomTextField(
                "Confirm Password",
                confirmPass,
                onChangeText = { confirmPass = it },
                "Enter your password",
                "",
                false
            )
            Spacer(Modifier.padding(bottom = 30.dp))
            TextFieldDropDownMenu()



            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column() {
                    CustomButton("Sign Up")
                    Row(Modifier.fillMaxWidth().padding(top = 30.dp),
                        horizontalArrangement = Arrangement.Center) {
                        Text(
                            "Already have an account?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.Dark_Arsenic)
                        )
                        Text(
                            " Login",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue,
                            modifier = Modifier.clickable {
                                controller.navigate(Screen.topNavigationBar.Authorization.tRoute)
                            })
                    }
                }
            }


        }

    }
}

@Composable
fun CustomTextField(
    title: String,
    text: String,
    onChangeText: (String) -> Unit,
    placeholder: String,
    errorMassage: String,
    readOnly: Boolean
) {
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            title,
            fontSize = 14.sp,
            color = colorResource(R.color.Grey),
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                onChangeText(it)
            },
            placeholder = {
                Text(text = placeholder, color = colorResource(R.color.Grey))
            },
            shape = RoundedCornerShape(24.dp),
            trailingIcon = {
                if (title == "Password" || title == "Confirm Password")
                    IconButton({
                        TODO()
                    }) {
                        Icon(painter = painterResource(R.drawable.hide), "")
                    }
            },
            readOnly = readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp, bottom = 10.dp),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(R.color.backgroung_textField2),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = colorResource(R.color.label_blueColor)

            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },

            )
    }

}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun TextFieldDropDownMenu() {
    val options = listOf("Admin", "User")
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            placeholder = {
                Text(text = "Type of account", color = colorResource(R.color.Grey))
            },
            shape = RoundedCornerShape(24.dp),
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp, bottom = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(R.color.backgroung_textField2),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = colorResource(R.color.label_blueColor)
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        text = option
                        expanded = false
                        focusManager.clearFocus()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )


            }
        }
    }

}
