package com.example.quickidenti.screens

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.api.People
import com.example.quickidenti.app.people
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.user
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
import java.nio.ByteBuffer

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PersonInfoScreen() {


    val changesSaved = stringResource(id = R.string.changes_saved)
    val context = LocalContext.current
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { result.value = it }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if (it) {
            launcher.launch()
        }
    }
    val peopleApi = retrofit.create(People::class.java)
    val upd: Array<String> = updatePeopleData(peopleApi)
    val fullName = remember { mutableStateOf(upd[0])}
    val birthdateValue = remember{ mutableStateOf(upd[1])}
    val phoneValue = remember{ mutableStateOf(upd[2])}
    val photoByteArray = ByteBuffer.wrap(upd[3].toByteArray())
    Log.i("bytea", photoByteArray.toString())
//    val photo = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.size))}

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
//                if(people.intValue != -1){
//                        Image(
//                            bitmap = photo.value.asImageBitmap(),
//                            contentDescription = "PersonPhoto",
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    }
//                result.value?.let { image ->
//                    Image(
//                        bitmap = image.asImageBitmap(),
//                        contentDescription = "PersonPhoto",
//                        modifier = Modifier.fillMaxSize()
//                    )
//                }
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
                textValue = birthdateValue.value,
                onValueChange = {birthdateValue.value = it},
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

@SuppressLint("SuspiciousIndentation")
fun updatePeopleData(peopleApi: People): Array<String>{
    var fullname = ""
    var birthdate = ""
    var phone = ""
    var photo = ""

    CoroutineScope(Dispatchers.IO).launch {
        val currentPeople = peopleApi.getPeople(user.value, people.intValue)
        Thread.sleep(100)
        fullname = currentPeople.fullname
        birthdate = currentPeople.birthdate
        phone = currentPeople.phone
        photo = currentPeople.photo
    }
    while((fullname == "") or (birthdate== "") or (phone== "") or (photo == ""))
    Thread.sleep(10)
    return arrayOf(fullname, birthdate, phone, photo)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PersonInfoScreenPreview(){
    PersonInfoScreen()
}