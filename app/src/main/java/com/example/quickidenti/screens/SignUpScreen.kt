package com.example.quickidenti.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.token
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.ClickableTextComponent
import com.example.quickidenti.components.MaskVisualTransformation
import com.example.quickidenti.components.PasswordTextFieldComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.dto.client.request.ClientReg
import com.example.quickidenti.messages.messages
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.regex.Pattern.matches

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SignUpScreen(){

    val context = LocalContext.current
    val password = remember { mutableStateOf("") }
    val isSuccess = remember { mutableStateOf(false)}
    val passwordToSubmit = remember { mutableStateOf("") }
    val emailValue = rememberSaveable{ mutableStateOf("") }
    val phoneValue = rememberSaveable{ mutableStateOf("")}
    val clientApi = retrofit.create(com.example.quickidenti.api.Client::class.java)


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(28.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center) {
            TextComponent(
                value = stringResource(id = R.string.registration),
                fontWeight = FontWeight.Bold,
                fontSize = 30,
                heightIn = 40)
            Spacer(modifier = Modifier.height(40.dp))
            TextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.email_outline),
                textValue = emailValue.value,
                withoutPatternField = true,
                onValueChange = {emailValue.value = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.lock_outline),
                password = password.value,
                onPassChange = {
                    password.value = it
                })
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password_to_submit),
                painterResource = painterResource(id = R.drawable.lock_outline),
                password = passwordToSubmit.value,
                onPassChange = {
                    passwordToSubmit.value = it
                }
            )
            TextFieldComponent(
                labelValue = stringResource(id = R.string.phone_number),
                painterResource = painterResource(id = R.drawable.phone_outline),
                textValue = phoneValue.value,
                onValueChange = { if(it.length <= 10){phoneValue.value = it}},
                mask = MaskVisualTransformation("+7(XXX) XXX-XX-XX"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(
                value = stringResource(id = R.string.sign_up)
            ) {
                if(matches("(.+@)((mail\\.(com|ru))|(yandex\\.ru))", emailValue.value)) {
                    if (password.value == passwordToSubmit.value) {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                var phone = ""
                                if(phoneValue.value.isNotEmpty()){
                                    phone = "+7" + phoneValue.value
                                }
                                val result = clientApi.regClient(
                                    ClientReg(
                                        emailValue.value,
                                        password.value,
                                        phone
                                    )
                                )
                                isSuccess.value = result.enter
                                if (isSuccess.value) {
                                    token.value = result.token
                                    QuickIdentiAppRouter.navigateTo(Screen.InfoScreen, true)
                                } else {
                                    Log.i("emailAlreadyExist", "email already exist")
                                    messages(context, "email_already_exist")
                                }
                            } catch (timeOut: SocketTimeoutException) {
                                Log.i("serverError", "server not found")
                                messages(context, "server")
                            }
                        }
                    } else
                        messages(context, "passwords_are_not_equals")
                }else
                    messages(context, "entering_data_incorrect")
            }
            Spacer(modifier = Modifier.height(100.dp))
            ClickableTextComponent(
                value = stringResource(id = R.string.already_have_account),
                onTextSelected = {
                    QuickIdentiAppRouter.navigateTo(Screen.SignInScreen, true)
                })
        }
    }
        BackHandler(enabled = true){
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}