package com.example.onlinestore.views.change_picture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.onlinestore.R

//@Composable
//fun AlertDialogG(
//    onDismiss:()->Unit
//) {
//    androidx.compose.material.AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {},
//        modifier = Modifier
//            .height(340.dp)
//            .width(328.dp),
//        shape = RoundedCornerShape(12.dp),
//
//
//        title = {
//                Text(
//                    text = "Change your picture",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                )
//        },
//
//
//        text = {
//
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//
//            )
//            {
//                Box(
//                    modifier = Modifier
//                        .width(296.dp)
//                        .height(60.dp)
//                        .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
//                        .clickable(onClick = {})
//                        .padding(10.dp)
//
//                ) {
//                    Row {
//
//
//                        Image(
//                            painter = painterResource(id = R.drawable.fotofoto),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .width(21.27.dp)
//                                .height(24.dp)
//                        )
//                        Spacer(
//                            modifier = Modifier
//                                .width(10.dp)
//                        )
//                        Text(text = "Take a photo")
//                    }
//                }
//                Box(
//                    modifier = Modifier
//                        .width(296.dp)
//                        .height(60.dp)
//                        .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
//                        .clickable(onClick = {})
//                        .padding(10.dp)
//
//                ) {
//                    Row {
//
//
//                        Image(
//                            painter = painterResource(id = R.drawable.picturefile),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .width(21.27.dp)
//                                .height(24.dp)
//                        )
//                        Spacer(
//                            modifier = Modifier
//                                .width(10.dp)
//                        )
//                        Text(text = "Choose from your file")
//                    }
//                }
//                Box(
//                    modifier = Modifier
//                        .width(296.dp)
//                        .height(60.dp)
//                        .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
//                        .clickable(onClick = {})
//                        .padding(10.dp)
//
//                ) {
//                    Row{
//
//                    Image(
//                        painter = painterResource(id = R.drawable.picruredelet),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .width(21.27.dp)
//                            .height(24.dp)
//                    )
//                        Spacer(
//                            modifier = Modifier
//                                .width(10.dp)
//                            )
//
//                    Text(text = "Delete Photo", color = Color.Red)
//                    }
//                }
//            }
//        }
//
//    )
//}


@Composable
fun EditProfileDialog(
    onDismiss: () -> Unit,
    toTakePhoto: () -> Unit,
    toFindPhotoDir: () -> Unit,
    toDeletePhoto: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .width(328.dp)
                .height(340.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change your picture",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.padding(16.dp))

                Button(
                    onClick = { toTakePhoto() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { toTakePhoto() },
                    colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)),
                    shape = RoundedCornerShape(12)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.fotofoto),
                            contentDescription = "",
                            modifier = Modifier
                                .size(17.72.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Take a photo",
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W700,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                letterSpacing = 0.5.sp,
                                textAlign = TextAlign.Start
                            ),
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { toFindPhotoDir() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { toFindPhotoDir() },
                    colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)),
                    shape = RoundedCornerShape(12)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.picturefile),
                            contentDescription = "",
                            modifier = Modifier
                                .size(17.72.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Choose from your file",
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W700,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                letterSpacing = 0.5.sp,
                                textAlign = TextAlign.Start
                            ),
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { toDeletePhoto() },
                    colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)),
                    shape = RoundedCornerShape(12)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.picruredelet),
                            contentDescription = "",
                            modifier = Modifier
                                .size(17.72.dp),
                            tint = Red
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Delete photo",
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.W700,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                letterSpacing = 0.5.sp,
                                textAlign = TextAlign.Start
                            ),
                            color = Red
                        )
                    }
                }
            }
        }
    }
}