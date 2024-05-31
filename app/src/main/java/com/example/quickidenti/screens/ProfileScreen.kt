package com.example.quickidenti.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.MainActivity
import com.example.quickidenti.R
import com.example.quickidenti.api.Client
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.token
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.LoadingComponent
import com.example.quickidenti.components.MaskVisualTransformation
import com.example.quickidenti.components.PasswordTextFieldComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.dto.client.request.ClientChangeData
import com.example.quickidenti.messages.messages
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.net.SocketTimeoutException
import java.util.regex.Pattern
import kotlin.system.exitProcess


@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun ProfileScreen(){
    val backCount = remember { mutableIntStateOf(1) }
    val context = LocalContext.current
    val newPassword = remember { mutableStateOf("") }
    val passwordToSubmit = remember { mutableStateOf("")}
    val oldPassword = remember { mutableStateOf("")}
    val emailValue = rememberSaveable{ mutableStateOf("") }
    val phoneValue = rememberSaveable{ mutableStateOf("")}
    val changesSavedStatus = remember { mutableStateOf(false)}
    val emailLabel = remember { mutableStateOf("")}
    val phoneLabel = remember { mutableStateOf("")}
    val daysLeft = remember { mutableIntStateOf(0) }
    val slots = remember { mutableIntStateOf(0)}
    val slotsOccupied = remember { mutableIntStateOf(0)}
    val dataReceived = remember { mutableStateOf(false)}
    val clientApi = retrofit.create(Client::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val client = clientApi.infoClient(token.value)
            sleep(200)
            emailLabel.value = client.email
            phoneLabel.value = client.phone
            slots.intValue = client.slots
            slotsOccupied.intValue = client.slots_occupied
            daysLeft.intValue = client.days
            dataReceived.value = true
        }catch (timeOut: SocketTimeoutException){
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
            Column(
                modifier = Modifier
                    .verticalScroll(ScrollState(0))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                TextFieldComponent(
                    labelValue = emailLabel.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    textValue = emailValue.value,
                    onValueChange = { emailValue.value = it },
                    painterResource = painterResource(
                        id = R.drawable.email_outline
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextFieldComponent(
                    labelValue = phoneLabel.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    textValue = phoneValue.value,
                    onValueChange = { if(it.length <= 10){ phoneValue.value = it }},
                    mask = MaskVisualTransformation("+7(XXX) XXX-XX-XX"),
                    painterResource = painterResource(
                        id = R.drawable.phone_outline
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))
                TextComponent(
                    value = "${stringResource(id = R.string.days_left)}: ${daysLeft.intValue}",
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextComponent(
                    value = "${stringResource(id = R.string.slots)}: ${slotsOccupied.intValue}/${slots.intValue}",
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(40.dp))

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.new_password),
                    painterResource = painterResource(id = R.drawable.lock_outline),
                    password = newPassword.value,
                    onPassChange = {
                        newPassword.value = it
                    }
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password_to_submit),
                    painterResource = painterResource(id = R.drawable.lock_outline),
                    password = passwordToSubmit.value,
                    onPassChange = {
                        passwordToSubmit.value = it
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.old_password),
                    painterResource = painterResource(id = R.drawable.lock_outline),
                    password = oldPassword.value,
                    onPassChange = {
                        oldPassword.value = it
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))
                ButtonComponent(value = stringResource(id = R.string.save_changes)) {
                    if ((Pattern.matches(
                            "(.+@)((mail\\.(com|ru))|(yandex\\.ru))",
                            emailValue.value
                        ) || emailValue.value == "")
                        && (Pattern.matches(
                            "(\\+|^)\\d{11}",
                            phoneValue.value
                        ) || phoneValue.value == "")
                    ) {
                        if (newPassword.value == passwordToSubmit.value) {
                            if (newPassword.value != oldPassword.value) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        changesSavedStatus.value = clientApi.changeClientData(
                                            token.value,
                                            ClientChangeData(
                                                emailValue.value,
                                                newPassword.value,
                                                oldPassword.value,
                                                "+7" + phoneValue.value
                                            )
                                        )
                                        if (changesSavedStatus.value) {
                                            dataReceived.value = false
                                            val client = clientApi.infoClient(token.value)
                                            emailLabel.value = client.email
                                            phoneLabel.value = client.phone
                                            messages(context, "changes_saved")
                                            oldPassword.value = ""
                                            newPassword.value = ""
                                            passwordToSubmit.value = ""
                                            slots.intValue = client.slots
                                            slotsOccupied.intValue = client.slots_occupied
                                            daysLeft.intValue = client.days
                                        } else {
                                            messages(context, "current_password_incorrect")
                                            oldPassword.value = ""
                                        }
                                        dataReceived.value = true
                                    } catch (timeOut: SocketTimeoutException) {
                                        Log.i("serverError", "server not found")
                                        messages(context, "server")
                                    }
                                }
                            } else
                                messages(context, "new_pass_duplicate_current")
                        } else
                            messages(context, "passwords_are_not_equals")
                    } else
                        messages(context, "entering_data_incorrect")
                }
            }
        }
    }else{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            LoadingComponent()
        }
    }
    BackHandler(enabled = true) {
        if(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value] != Screen.SignInScreen && QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value] != Screen.SignUpScreen) {
            QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
        }else{
            if(backCount.intValue == 2){
                val activity = MainActivity()
                activity.finish()
                val exitApi = retrofit.create(Client::class.java)
                CoroutineScope(Dispatchers.IO).launch{
                    exitApi.clientExit(token.value)
                    exitProcess(0)
                }
            }
            if(backCount.intValue == 1)
                messages(context, "exit_from_app")
            backCount.intValue++
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}