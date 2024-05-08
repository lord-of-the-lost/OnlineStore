package com.example.onlinestore.views.change_picture


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.onlinestore.R
import com.example.onlinestore.ui.theme.plusJakartaSans


@Composable
fun ChangePhotoDialog(
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
                .width(328.dp)
                .height(340.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change your picture",
                    style = TextStyle(
                        fontFamily = plusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(19.dp))
                Box(
                    modifier = Modifier
                        .border(1.dp, Color(0xFFD9D9D9))
                        .fillMaxWidth()
                        .size(0.dp, 1.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                {
                    CustomButtonForDialog(
                        text = "Take a photo",
                        iconId = R.drawable.take_a_photo_icon,
                        onClick = toTakePhoto
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomButtonForDialog(
                        text = "Choose from your file",
                        iconId = R.drawable.ic_file,
                        onClick = toFindPhotoDir
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomButtonForDialog(
                        text = "Delete Photo",
                        iconId = R.drawable.ic_trash,
                        onClick = toDeletePhoto,
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
        shape = RoundedCornerShape(8),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                modifier = Modifier.size(17.72.dp),
                tint = iconColor
            )
            Spacer(modifier = Modifier.width(19.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = plusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.5.sp,
                ),
                color = textColor
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChangePhotoDialogPreview() {
    ChangePhotoDialog({}, {}, {}, {}
    )
}