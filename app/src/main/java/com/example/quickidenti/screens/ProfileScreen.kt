package com.example.quickidenti.screens

import android.widget.Toast
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
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.PasswordTextFieldComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.components.TextFieldComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import java.util.regex.Pattern
import kotlin.system.exitProcess

@Composable
fun ProfileScreen(){

    val backCount = remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val newPassword = remember { mutableStateOf("") }
    val passwordToSubmit = remember { mutableStateOf("")}
    val oldPassword = remember { mutableStateOf("")}
    val emailValue = rememberSaveable{ mutableStateOf("") }
    val phoneValue = rememberSaveable{ mutableStateOf("")}
    val changesSaved = stringResource(id = R.string.changes_saved)
    val currentPasswordIncorrect = stringResource(id = R.string.current_password_incorrect)
    val newPassDuplicateCurrent = stringResource(id = R.string.new_pass_duplicate_current)
    val passwordsAreNotEquals =  stringResource(id = R.string.passwords_are_not_equals)
    val enteringDataIncorrect = stringResource(id = R.string.entering_data_incorrect)
    val exitFromApp = stringResource(id = R.string.exit_from_app)

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
                labelValue = stringResource(id = R.string.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textValue = emailValue.value,
                onValueChange = {emailValue.value = it},
                painterResource = painterResource(
                    id = R.drawable.email_outline
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent(
                labelValue = stringResource(id = R.string.phone_number),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                textValue = phoneValue.value,
                onValueChange = {phoneValue.value = it},
                painterResource = painterResource(
                    id = R.drawable.phone_outline
                )
            )

            Spacer(modifier = Modifier.height(40.dp))
            TextComponent(value = "${stringResource(id = R.string.days_left)}: 90", textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = "${stringResource(id = R.string.slots)}: 30/50", textAlign = TextAlign.Start)
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
                if(Pattern.matches("(.+@)((mail\\.(com|ru))|(yandex\\.ru))", emailValue.value)
                    && Pattern.matches("(\\+|^)\\d{11}", phoneValue.value)){
                    if (newPassword.value == passwordToSubmit.value) {
                        if(newPassword.value != oldPassword.value) {
                            if (oldPassword.value == "123") {
                                Toast.makeText(context, changesSaved, Toast.LENGTH_SHORT).show()
                            } else
                                Toast.makeText(context, currentPasswordIncorrect, Toast.LENGTH_LONG).show()
                        }else
                            Toast.makeText(context, newPassDuplicateCurrent, Toast.LENGTH_LONG).show()
                    }else
                        Toast.makeText(context, passwordsAreNotEquals, Toast.LENGTH_LONG).show()
                }else
                    Toast.makeText(context, enteringDataIncorrect, Toast.LENGTH_LONG).show()
            }
        }
    }
    BackHandler(enabled = true) {
        if(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value] != Screen.SignInScreen && QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value] != Screen.SignUpScreen) {
            QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
        }else{
            if(backCount.intValue == 2){
                val activity = MainActivity()
                activity.finish()
                exitProcess(0)
            }
            backCount.intValue++
            Toast.makeText(context, exitFromApp, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}