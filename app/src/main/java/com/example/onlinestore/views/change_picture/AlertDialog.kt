package com.example.onlinestore.views.change_picture


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun ChangePhotoDialog(
    onDismiss: () -> Unit,
    toTakePhoto: () -> Unit,
    toFindPhotoDir: () -> Unit,
    toDeletePhoto: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Dialog(
            onDismissRequest = { onDismiss() },
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
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButtonForDialog(
                        text = "Take a photo",
                        iconId = R.drawable.ic_camera,
                        onClick = { toTakePhoto() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButtonForDialog(
                        text = "Choose from your file",
                        iconId = R.drawable.ic_file,
                        onClick = { toFindPhotoDir() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButtonForDialog(
                        text = "Delete photo",
                        iconId = R.drawable.ic_trash,
                        onClick = { toDeletePhoto() },
                        textColor = Color.Red,
                        iconColor = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun CustomButtonForDialog(
    text: String,
    iconId: Int,
    onClick: () -> Unit,
    textColor: Color = Color.Black,
    iconColor: Color = Color.Black,
    backgroundColor: Color = Color(0xFFF5F5F5)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor),
        shape = RoundedCornerShape(12)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                modifier = Modifier.size(17.72.dp),
                tint = iconColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Start
                ),
                color = textColor
            )
        }
    }
}
