package com.example.quickidenti.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.components.HeadingTextComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen

@Composable
fun SignUpScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(28.dp)) {
        HeadingTextComponent(value = "Registration")
    }
    BackHandler(enabled = true){
        QuickIdentiAppRouter.navigateTo(Screen.SignInScreen)
    }
}

@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}