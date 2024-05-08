package com.example.onlinestore.views.change_picture

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.navigation.Screen

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
                controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            }
        },
        modifier = modifier
    )
}

@Composable
fun Camera(
    viewModel: StoreViewModel,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE,
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CameraPreview(controller = controller, modifier = Modifier.fillMaxSize())

        IconButton(
            onClick = {
                onBackClick()
            }, modifier = Modifier.offset(16.dp, 16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = {
                    controller.cameraSelector =
                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else CameraSelector.DEFAULT_BACK_CAMERA
                }
            ) {
                Icon(imageVector = Icons.Default.Cameraswitch, contentDescription = null)
            }

            IconButton(
                onClick = {
                    controller.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                super.onCaptureSuccess(image)

                                val matrix = Matrix().apply {
                                    preScale(1f, -1f)
                                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                                }

                                val rotatedBitmap = Bitmap.createBitmap(
                                    image.toBitmap(),
                                    0,
                                    0,
                                    image.height,
                                    image.height,
                                    matrix,
                                    true
                                )

                                viewModel.bitmap.value = rotatedBitmap

                                onBackClick()
                            }

                            override fun onError(exception: ImageCaptureException) {
                                super.onError(exception)
                                Log.e("Camera", "Couldnt take photo", exception)
                            }
                        }
                    )
                }
            ) {
                Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null)
            }
        }
    }
}


