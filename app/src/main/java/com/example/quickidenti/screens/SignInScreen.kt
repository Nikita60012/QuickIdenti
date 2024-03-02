package com.example.quickidenti.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.ClickableTextComponent
import com.example.quickidenti.components.NormalTextComponent
import com.example.quickidenti.components.NormalTextFieldComponent
import com.example.quickidenti.components.PasswordTextFieldComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen

@Composable
fun SignInScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center){
            NormalTextComponent(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(40.dp))
            NormalTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.email_outline)
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.lock_outline)
            )
            Spacer(modifier = Modifier.height(20.dp))
            NormalTextComponent(value = stringResource(id = R.string.lost_password))
            Spacer(modifier = Modifier.height(100.dp))
            ButtonComponent(value = stringResource(id = R.string.sign_in))
            Spacer(modifier = Modifier.height(100.dp))
            ClickableTextComponent(
                value = stringResource(R.string.create_account),
                onTextSelected = {
                    QuickIdentiAppRouter.navigateTo(Screen.SignUpScreen)
                })
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){
    SignInScreen()
}