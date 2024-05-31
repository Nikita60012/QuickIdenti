package com.example.quickidenti.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.quickidenti.R
import com.example.quickidenti.api.Identification
import com.example.quickidenti.app.humanId
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.token
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.ImageButtonComponent
import com.example.quickidenti.components.LoadingComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.messages.messages
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun IdentificationResultScreen(){
    val context = LocalContext.current
    val conclusion = remember { mutableStateOf("")}
    val dataReceived = remember { mutableStateOf(false)}
    val knownPhoto = remember{ mutableStateOf<Bitmap?>(null)}
    val unknownPhoto = remember{ mutableStateOf<Bitmap?>(null)}
    val identificationApi = retrofit.create(Identification::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val data = identificationApi.getIdentification(token.value, 2)
            humanId.intValue = data.human_id
            conclusion.value = data.conclusion
            val loading = ImageLoader(context)
            val knownRequest = ImageRequest.Builder(context)
                .data("http:/10.0.2.2:8000/identification_people/identification_get_known_person_photo/${token.value}/2")
                .build()
            val knownImage = (loading.execute(knownRequest) as SuccessResult).drawable
            knownPhoto.value = knownImage.toBitmap()
            val unknownRequest = ImageRequest.Builder(context)
                .data("http:/10.0.2.2:8000/identification_people/identification_get_unknown_person_photo/${token.value}/2")
                .build()
            val unknownImage = (loading.execute(unknownRequest) as SuccessResult).drawable
            unknownPhoto.value = unknownImage.toBitmap()
            dataReceived.value = true
        }catch (e: SocketTimeoutException){
            Log.i("serverError", "server not found")
            messages(context, "server")
        }
    }
    if(dataReceived.value) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .height(220.dp)
                            .width(150.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(Primary, Secondary))
                            )
                    ){
                        val image =
                            knownPhoto.value?.let { Bitmap.createScaledBitmap(it, 150, 220, true) }
                        if (image != null) {
                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = "PersonPhoto",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Box(
                        modifier = Modifier
                            .height(220.dp)
                            .width(150.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(Primary, Secondary))
                            )
                    ){
                        val image =
                            unknownPhoto.value?.let { Bitmap.createScaledBitmap(it, 150, 220, true) }
                        if (image != null) {
                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = "PersonPhoto",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    TextComponent(value = conclusion.value, fontSize = 24)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    ImageButtonComponent(
                        imageVector = Icons.Default.Info,
                        description = "Worker Info"
                    ) {
                        QuickIdentiAppRouter.navigateTo(Screen.PersonInfoScreen, true)
                    }
                }
                Spacer(modifier = Modifier.height(70.dp))
                ButtonComponent(value = stringResource(id = R.string.back_to_identification)) {
                    QuickIdentiAppRouter.navigateTo(Screen.IdentificationScreen, false)
                }

            }
        }
    }else{
        LoadingComponent()
    }
    BackHandler(enabled = true) {
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun IdentificationResultScreenPreview(){
    IdentificationResultScreen()
}