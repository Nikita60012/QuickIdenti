@file:Suppress("DEPRECATION")

package com.example.quickidenti.screens

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.quickidenti.R
import com.example.quickidenti.api.People
import com.example.quickidenti.app.people
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.user
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Thread.sleep


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PersonInfoScreen() {


    val changesSaved = stringResource(id = R.string.changes_saved)
    val context = LocalContext.current
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val newPhoto = remember { mutableStateOf<Uri>(Uri.EMPTY)}
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        val bytes = ByteArrayOutputStream()
        it?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            it,
            "photo",
            null
        )
        result.value = it
        newPhoto.value = Uri.parse(path)}
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
    if(people.intValue != -1) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentPeople = humanApi.getHuman(user.value, people.intValue)
            sleep(100)
            fullName.value = currentPeople.fullname
            birthdateValue.value = currentPeople.birthdate
            phoneValue.value = currentPeople.phone
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data("http:/10.0.2.2:8000/edit_workers/get_human_photo/15")
                .build()
            val image = (loading.execute(request) as SuccessResult).drawable
            photo.value = image.toBitmap()
            contentReceived.value = true
        }
    }

    if (contentReceived.value or (people.intValue == -1)) {
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
                Box(modifier = Modifier
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
                    if (people.intValue != -1) {
                        photo.value?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "PersonPhoto",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    result.value?.let { image ->
                        tempPhoto.value = image
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
                    painterResource = null
                )
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.birthdate),
                    textValue = birthdateValue.value,
                    onValueChange = { birthdateValue.value = it },
                    painterResource = null
                )
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.phone_number),
                    textValue = phoneValue.value,
                    onValueChange = { phoneValue.value = it },
                    painterResource = null
                )
                Spacer(modifier = Modifier.height(60.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    ButtonComponent(
                        value = stringResource(id = R.string.save),
                        modifier = Modifier
                            .width(200.dp)
                            .heightIn(48.dp)
                            .weight(1.5f)
                            .padding(5.dp)
                    ) {
                        Toast.makeText(context, changesSaved, Toast.LENGTH_SHORT).show()
                        CoroutineScope(Dispatchers.IO).launch {
//                            val wrapper = ContextWrapper(context)
//                            var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
//                            file = File(file,"${UUID.randomUUID()}.jpg")
//                            val bytes = ByteArrayOutputStream()
//                            tempPhoto.value?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                            val path: String = MediaStore.Images.Media.insertImage(
//                                context.contentResolver,
//                                tempPhoto.value,
//                                "Title",
//                                null
//                            )
//                            val uri: Uri = Uri.parse(path)
//                            newPhoto.value = uri.path?.let { File(it) }
                            val jpegBytes = newPhoto.value.path?.let { File(it).readBytes() }
                            val requestBody = jpegBytes?.toRequestBody("image/*".toMediaType())
                            val body = requestBody?.let {
                                MultipartBody.Part.createFormData("photo", "photo.jpg",
                                    it
                                )
                            }
                            val photoAddCheck = humanApi.photoAdd(body)
                            Log.i("check", photoAddCheck.toString())
//                            val addInfo = HumanAdd(
//                                    fullname = fullName.value,
//                                    birthdate = birthdateValue.value,
//                                    phone = phoneValue.value,
//                                    )
//                            humanApi.addHuman(user.value, addInfo, newPhoto.value!!)
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