package com.example.quickidenti.screens

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary



@Composable
fun IdentificationScreen(){
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        result.value = it
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            launcher.launch()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround){
            Box(modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(listOf(Primary, Secondary))
                )
                .clickable(onClick = {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }))
            {
                result.value?.let { image ->
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "PersonPhoto",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            ButtonComponent(value = stringResource(id = R.string.identification)) {
                QuickIdentiAppRouter.navigateTo(Screen.IdentificationResultScreen, true)
            }
        }
    }
    BackHandler(enabled = true) {
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun IdentificationScreenPreview(){
    IdentificationScreen()
}

