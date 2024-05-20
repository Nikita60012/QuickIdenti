package com.example.quickidenti.screens
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import com.example.quickidenti.app.user
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.ClickableTextComponent
import com.example.quickidenti.components.PasswordTextFieldComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.dto.client.clientAuth
import com.example.quickidenti.exeptions.exeptions
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@SuppressLint("SuspiciousIndentation")
@Composable
fun SignInScreen(){

    val password = rememberSaveable{ mutableStateOf("") }
    val emailValue = rememberSaveable{ mutableStateOf("") }
    val context = LocalContext.current
    val incorrectData = stringResource(id = R.string.email_or_pass_incorrect)
    val clientApi = retrofit.create(com.example.quickidenti.api.Client::class.java)
    val changeFail = remember { (mutableStateOf(false)) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center){
            TextComponent(
                value = stringResource(id = R.string.welcome),
                fontWeight = FontWeight.Bold,
                fontSize = 30,
                heightIn = 40)
            Spacer(modifier = Modifier.height(40.dp))
            TextFieldComponent(
                labelValue = "${stringResource(id = R.string.email)} (123@mail.com)",
                painterResource = painterResource(id = R.drawable.email_outline),
                textValue = emailValue.value,
                onValueChange = {emailValue.value = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            PasswordTextFieldComponent(
                labelValue = "${stringResource(id = R.string.password)} 123",
                painterResource = painterResource(id = R.drawable.lock_outline),
                password = password.value,
                onPassChange = {password.value = it})
            Spacer(modifier = Modifier.height(20.dp))
            TextComponent(value = stringResource(id = R.string.lost_password))
            Spacer(modifier = Modifier.height(100.dp))
            ButtonComponent(
                value = stringResource(id = R.string.sign_in),
                action = {
                        CoroutineScope(Dispatchers.IO).launch {
                            try{
                                if (clientApi.authClient(
                                        clientAuth(
                                            emailValue.value,
                                            password.value
                                        )
                                    )
                                ) {
                                    changeFail.value = false
                                    user.value = emailValue.value
                                    QuickIdentiAppRouter.navigateTo(Screen.InfoScreen, true)
                                } else
                                    changeFail.value = true
                                password.value = ""
                            }catch (timeOut: SocketTimeoutException){
                                Log.i("serverError", "server not found")
                                exeptions(context, "server")
                            }
                        }
                        if (changeFail.value)
                            Toast.makeText(context, incorrectData, Toast.LENGTH_LONG).show()
                })
            Spacer(modifier = Modifier.height(100.dp))
            ClickableTextComponent(
                value = stringResource(R.string.create_account),
                onTextSelected = {
                    QuickIdentiAppRouter.navigateTo(Screen.SignUpScreen, true)
                })
        }
    }
    BackHandler(enabled = true){
        if(QuickIdentiAppRouter.historyScreenList.isNotEmpty())
            QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){
    SignInScreen()
}