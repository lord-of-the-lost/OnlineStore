package com.example.onlinestore.views.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.navigation.Screen
import com.example.onlinestore.ui.theme.CustomGrey2
import com.example.onlinestore.views.change_picture.ChangePhotoDialog

@Composable
fun ProfileScreen(navController: NavController, viewModel: StoreViewModel) {
    var showAlertDialog by remember { mutableStateOf(false) }
    var showChangePhotoDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(Modifier.height(14.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 27.dp)
                    .size(102.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "profile picture",
                    Modifier
                        .width(140.dp)
                        .height(120.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(100))
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .fillMaxSize()
                        .clickable { showChangePhotoDialog = true }
                )
            }
            Box(
                modifier = Modifier
                    .height(69.5.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Dev P",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            lineHeight = 24.sp,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "dev@gmail.com",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 24.sp,
                            color = CustomGrey2,
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Start,
                        )
                    )
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null,
                            modifier = Modifier.clickable { }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "***********", style = TextStyle(color = CustomGrey2))
                    }
                }
            }
        }
        Spacer(Modifier.weight(1.0f))
        ActionButton(title = "Type of Account", painterResource = R.drawable.ic_arrow) {
            showAlertDialog = true
        }
        Spacer(Modifier.height(22.dp))
        ActionButton(title = "Terms & Conditions", painterResource = R.drawable.ic_arrow) {
            navController.navigate(Screen.NavigationItem.TermsConditions.route)
        }
        Spacer(Modifier.height(22.dp))
        ActionButton(title = "Sign Out", painterResource = R.drawable.ic_signout) {
            navController.navigate(Screen.NavigationItem.Onboarding.route)
        }
        Spacer(Modifier.height(33.dp))
    }
    if (showAlertDialog) {
        AlertDialogTypeAccount(
            onDismissRequest = { showAlertDialog = false },
            onConfirmation = { showAlertDialog = false },
            "Select type of account",
            password = "0000"
        )
    }
    if (showChangePhotoDialog) {
        ChangePhotoDialog(
            onDismiss = { showChangePhotoDialog = false },
            toTakePhoto = {
                showChangePhotoDialog = false
            },
            toFindPhotoDir = {
                showChangePhotoDialog = false
            },
            toDeletePhoto = {
                showChangePhotoDialog = false
            }
        )
    }
}

@Composable
fun ActionButton(title: String, painterResource: Int, action: () -> Unit) {
    Button(
        onClick = { action() },
        modifier = Modifier
            .height(56.dp)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFFF3F4F6))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 24.sp,
                    color = Color(0xFF666C8E),
                ),
                textAlign = TextAlign.Start,
            )
            Image(
                painter = painterResource(id = painterResource),
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                alignment = Alignment.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogTypeAccount(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    password: String
) {
    var select by remember { mutableStateOf(true) }
    var select2 by remember { mutableStateOf(false) }
    var showPasswordField by remember { mutableStateOf(false) }
    var inputPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val visibility =
        if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    var Error by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton({
                onConfirmation()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton({
                onDismissRequest()
            }) {
                Text("Dismiss")
            }
        },
        title = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(dialogTitle, fontSize = 25.sp)
            }
        },

        text = {
            Column() {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Card(
                        Modifier
                            .size(100.dp)
                            .padding()
                            .clickable {
                                select = true
                                showPasswordField = false
                                select2 = false
                                Error = false
                            }
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(13.dp)),
                        colors = CardDefaults.cardColors(
                            if (!select) Color.White else colorResource(
                                R.color.Green_Sheen
                            )
                        ),
                        shape = RoundedCornerShape(13.dp)
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("User")
                        }
                    }
                    Spacer(Modifier.padding(end = 10.dp))
                    Card(Modifier
                        .size(100.dp)
                        .padding()
                        .clickable {
                            select2 = true
                            select = false
                            showPasswordField = true

                        }
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(13.dp)),
                        colors = CardDefaults.cardColors(
                            if (!select2) Color.White else colorResource(
                                R.color.Green_Sheen
                            )
                        ),
                        shape = RoundedCornerShape(13.dp)) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Manager")
                        }
                    }
                }

                if (showPasswordField) {
                    val focusManager = LocalFocusManager.current
                    OutlinedTextField(
                        enabled = if (password == inputPassword) false else true,
                        value = inputPassword,
                        onValueChange = {
                            inputPassword = it
                            if (inputPassword != "") Error = false
                        },
                        placeholder = {
                            Text(text = "Enter password", color = colorResource(R.color.Grey))
                        },
                        shape = RoundedCornerShape(24.dp),
                        trailingIcon = {
                            if (inputPassword != password) {
                                IconButton(onClick = {
                                    passwordVisibility = !passwordVisibility
                                }) {
                                    Icon(painter = painterResource(R.drawable.hide), "")
                                }
                            } else {
                                Icon(Icons.Default.Check, "")
                            }


                        },

                        visualTransformation = visibility,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 7.dp, bottom = 10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = colorResource(
                                R.color.backgroung_textField2
                            ),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = colorResource(R.color.backgroung_textField2),
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword).copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (inputPassword != password) {
                                Error = true
                                inputPassword = ""
                            }
                            focusManager.clearFocus()
                        }),
                        isError = Error
                    )

                }
                if (Error) {
                    Text("invalid password", color = Color.Red)
                }
            }


        },
        containerColor = Color.White
    )
}
