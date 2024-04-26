package com.example.onlinestore.views.AuthentificationScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinestore.R
import com.example.onlinestore.navigation.Screen

@Composable
fun LoginScreen(controller:NavController) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, start = 20.dp, end = 20.dp)
                .background(Color.White)

        ) {
            Text("Введите логин", fontSize = 14.sp, color = colorResource(R.color.Grey))
            TextField(text = login, { login = it }, "Логин")

            Spacer(modifier = Modifier.padding(bottom = 10.dp))

            Text("Пароль", fontSize = 14.sp, color = colorResource(R.color.Grey))
            TextField(text = password, { login = it }, "Пароль")

            Box(
                Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                CustomButton("Войти")

            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "У вас еще нет аккаунта?",
                        color = colorResource(R.color.Dark_Arsenic),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Зарегистрироваться",
                        color = colorResource(R.color.Green_Sheen),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable {
                            controller.navigate(Screen.topNavigationBar.Registration.route)
                        }
                    )
                }
            }

        }
    }

}


@Composable
fun CustomButton(text:String){
    Button(
        {
            TODO()
        },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.Green_Sheen),
        )
    ) {
        Text(text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TextField(
    text: String,
    textChanged: (String) -> Unit,
    placeholder: String
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = text,
        onValueChange = {
            textChanged(it)
        },
        placeholder = {
            Text(placeholder, color = colorResource(R.color.Grey))
        },
        trailingIcon = {
            Icon(painter = painterResource(R.drawable.eye), "", tint = colorResource(R.color.Grey))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.border_color),
            focusedBorderColor = colorResource(R.color.label_blueColor),
            backgroundColor = colorResource(R.color.background_textField)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            focusManager.clearFocus()
        },
        singleLine = true
    )

}