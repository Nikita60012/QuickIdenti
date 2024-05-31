package com.example.quickidenti.screens

import android.Manifest
import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.api.Identification
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.token
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.convertBitmap2File
import com.example.quickidenti.messages.messages
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun IdentificationScreen(){
    val context = LocalContext.current
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        result.value = it
    }
    val operationSuccess = remember { mutableStateOf(false)}
    val identificationApi = retrofit.create(Identification::class.java)
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
                    Log.i("image", image.asImageBitmap().toString())
                }
            }
            ButtonComponent(value = stringResource(id = R.string.identification)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val status = identificationApi.checkSubscribe(token.value)
                    if(status.date_status) {
                        try{
                        val photo = result.value?.let { convertBitmap2File(context, it) }
                        photo?.let {
                            operationSuccess.value =
                                identificationApi.identification(token.value, it)
                        }
                        }catch (e: NullPointerException){
                            Log.e("no_photo", "user don`t make photo")
                            messages(context, "no_photo")
                        }
                    }else{
                        Log.i("subscribe_end", "user subscribe is end")
                        messages(context, "subscribe_end")
                    }
                }
                if (operationSuccess.value) {
                    QuickIdentiAppRouter.navigateTo(Screen.IdentificationResultScreen, true)
                }else{
                    Log.i("wrong_photo", "photo is incorrect")
                    messages(context, "wrong_photo")
                }
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

