package com.example.quickidenti.screens

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PersonInfoScreen() {



    val changesSaved = stringResource(id = R.string.changes_saved)
    val context = LocalContext.current
    val fullName = rememberSaveable { mutableStateOf("")}
    val phoneValue = rememberSaveable{ mutableStateOf("")}
    val birthdate = rememberSaveable{ mutableStateOf("")}
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { result.value = it }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if (it) {
            launcher.launch()
        }
    }

    val workerApi = retrofit.create(com.example.quickidenti.api.Worker::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        val worker = workerApi.getWorker()
        fullName.value = worker.fullname
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(ScrollState(0))){
            Box(modifier = Modifier
                .height(220.dp)
                .width(200.dp)
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
            Spacer(modifier = Modifier.height(50.dp))
            TextComponent(value = stringResource(id = R.string.fullname), textAlign = TextAlign.Start)
            TextFieldComponent(labelValue = "",
                textValue = fullName.value,
                onValueChange = {fullName.value = it},
                painterResource = null)
            Spacer(modifier = Modifier.height(30.dp))

            TextComponent(value = stringResource(id = R.string.birthdate), textAlign = TextAlign.Start)
            TextFieldComponent(labelValue = "",
                textValue = birthdate.value,
                onValueChange = {birthdate.value = it},
                painterResource = null)
            Spacer(modifier = Modifier.height(30.dp))

            TextComponent(value = stringResource(id = R.string.phone_number), textAlign = TextAlign.Start)
            TextFieldComponent(labelValue = "",
                textValue = phoneValue.value,
                onValueChange = {phoneValue.value = it},
                painterResource = null)
            Spacer(modifier = Modifier.height(60.dp))

            Row(modifier = Modifier.fillMaxWidth()){
                ButtonComponent(value = stringResource(id = R.string.save),
                    modifier = Modifier
                        .width(200.dp)
                        .heightIn(48.dp)
                        .weight(1.5f)
                        .padding(5.dp)) {
                    Toast.makeText(context, changesSaved, Toast.LENGTH_SHORT).show()
                }
                ButtonComponent(value = stringResource(id = R.string.back),
                    modifier = Modifier
                        .width(200.dp)
                        .heightIn(48.dp)
                        .weight(1f)
                        .padding(5.dp)) {
                    QuickIdentiAppRouter.navigateTo(Screen.PeopleListScreen, false)
                }
            }

        }
    }
    BackHandler(enabled = true){
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun PersonInfoScreenPreview(){
    PersonInfoScreen()
}