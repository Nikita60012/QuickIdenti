
package com.example.quickidenti.screens

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.quickidenti.R
import com.example.quickidenti.api.People
import com.example.quickidenti.app.humanId
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.token
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.LoadingComponent
import com.example.quickidenti.components.MaskVisualTransformation
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.components.convertBitmap2File
import com.example.quickidenti.dto.human.HumanAdd
import com.example.quickidenti.dto.human.HumanUpdate
import com.example.quickidenti.messages.messages
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.net.SocketTimeoutException


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun PersonInfoScreen() {


    val context = LocalContext.current
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val operationSuccess = remember { mutableStateOf(false)}
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { result.value = it}
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if (it) {
            launcher.launch()
        }
    }
    val tempPhoto = remember { mutableStateOf<Bitmap?>(null)}
    val contentReceived = remember { mutableStateOf(false)}
    val fullName = remember { mutableStateOf("")}
    val birthdateValue = remember{ mutableStateOf("")}
    val phoneValue = remember{ mutableStateOf("")}
    val photo = remember{ mutableStateOf<Bitmap?>(null)}
    val humanApi = retrofit.create(People::class.java)
    if(humanId.intValue != -1) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentPeople = humanApi.getHuman(token.value, humanId.intValue)
                sleep(100)
                fullName.value = currentPeople.fullname
                birthdateValue.value = currentPeople.birthdate
                phoneValue.value = currentPeople.phone
                val loading = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data("http:/192.168.0.106:8000/edit_people/get_human_photo/${token.value}/${humanId.intValue}")
                    .build()
                val image = (loading.execute(request) as SuccessResult).drawable
                photo.value = image.toBitmap()
                contentReceived.value = true
            }catch (e: SocketTimeoutException){
                Log.i("serverError", "server not found")
                messages(context, "server")
            }
        }
    }

    if (contentReceived.value or (humanId.intValue == -1)) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.verticalScroll(ScrollState(0))
            ) {
                Box(
                    modifier = Modifier
                        .height(220.dp)
                        .width(200.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(Primary, Secondary))
                        )
                        .clickable(onClick = {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        })
                )
                {
                    if (humanId.intValue != -1) {
                        photo.value?.let {
                            val image = Bitmap.createScaledBitmap(it, 200, 220, true)
                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = "PersonPhoto",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    result.value?.let {
                        tempPhoto.value = it
                        val image = Bitmap.createScaledBitmap(it, 200, 220, true)
                        Image(
                            bitmap = image.asImageBitmap(),
                            contentDescription = "PersonPhoto",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.fullname),
                    textValue = fullName.value,
                    onValueChange = { fullName.value = it },
                    withoutPatternField = true,
                    painterResource = null
                )
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.birthdate),
                    textValue = birthdateValue.value,
                    onValueChange = { if(it.length <= 8){ birthdateValue.value = it }},
                    mask = MaskVisualTransformation("XX-XX-XXXX"),
                    painterResource = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.phone_number),
                    textValue = phoneValue.value,
                    onValueChange = { if(it.length <= 10){ phoneValue.value = it }},
                    mask = MaskVisualTransformation("+7(XXX) XXX-XX-XX"),
                    painterResource = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(modifier = Modifier.height(60.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (humanId.intValue != -1) {
                        ButtonComponent(
                            value = stringResource(id = R.string.save),
                            modifier = Modifier
                                .width(200.dp)
                                .heightIn(48.dp)
                                .weight(1.5f)
                                .padding(5.dp)
                        ) {
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val photo2update =
                                        result.value?.let { convertBitmap2File(context, it) }
                                    val addInfo = photo2update?.let {
                                        HumanUpdate(
                                            fullname = fullName.value,
                                            birthdate = birthdateValue.value,
                                            phone = phoneValue.value,
                                            photo = it
                                        )
                                    }

                                    addInfo?.let {
                                        operationSuccess.value =
                                            humanApi.updateHuman(token.value, humanId.intValue, it)
                                    }
                                    if (operationSuccess.value) {
                                        messages(context, "changes_saved")
                                        operationSuccess.value = false
                                    } else {
                                        Log.i("wrong_photo", "photo is incorrect")
                                        messages(context, "wrong_photo")
                                    }
                                }
                            } catch (e: NullPointerException) {
                                Log.e("no_photo", "user don`t make photo")
                                messages(context, "no_photo")
                            }
                        }
                    } else {
                        ButtonComponent(
                            value = stringResource(id = R.string.add_human_data),
                            modifier = Modifier
                                .width(200.dp)
                                .heightIn(48.dp)
                                .weight(1.5f)
                                .padding(5.dp)
                        ) {
                            if (fullName.value != "") {

                                    CoroutineScope(Dispatchers.IO).launch {
                                        val status = humanApi.checkSubscribe(token.value)
                                        if (status.date_status) {
                                            if (status.slots_status) {
                                                val photo2add =
                                                    result.value?.let {
                                                        convertBitmap2File(
                                                            context,
                                                            it
                                                        )
                                                    }
                                                val addInfo =
                                                    photo2add?.let {
                                                        HumanAdd(
                                                            fullName.value,
                                                            birthdateValue.value,
                                                            phoneValue.value,
                                                            it
                                                        )
                                                    }
                                                try {
                                                operationSuccess.value = humanApi.addHuman(
                                                    token.value,
                                                    addInfo!!)
                                                    if (operationSuccess.value) {
                                                        messages(context, "human_added")
                                                        operationSuccess.value = false
                                                    } else {
                                                        Log.i("wrong_photo", "photo is incorrect")
                                                        messages(context, "wrong_photo")
                                                    }
                                                } catch (e: NullPointerException) {
                                                    Log.e("no_photo", "user don`t make photo")
                                                    messages(context, "no_photo")
                                                }
                                            } else {
                                                Log.i("slots_limit", "user slots are full")
                                                messages(context, "slots_limit")
                                            }
                                        } else {
                                            Log.i("subscribe_end", "user subscribe is end")
                                            messages(context, "subscribe_end")
                                        }
                                    }
                            } else {
                                messages(context, "fullname_add")
                            }
                        }
                        ButtonComponent(
                            value = stringResource(id = R.string.back),
                            modifier = Modifier
                                .width(200.dp)
                                .heightIn(48.dp)
                                .weight(1f)
                                .padding(5.dp)
                        ) {
                            QuickIdentiAppRouter.navigateTo(Screen.PeopleListScreen, false)
                        }
                    }

                }
            }
        }
    }else{
        LoadingComponent()
    }
    BackHandler(enabled = true){
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PersonInfoScreenPreview(){
    PersonInfoScreen()
}