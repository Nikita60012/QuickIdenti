package com.example.quickidenti.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.screens.SignInScreen
import com.example.quickidenti.screens.SignUpScreen


@Composable
fun QuickIdentiApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = QuickIdentiAppRouter.currentScreen, label = "") { currentState->
            when(currentState.value){
                is Screen.SignInScreen ->{
                    SignInScreen()
                }
                is Screen.SignUpScreen ->{
                    SignUpScreen()
                }
            }
        }
    }
}
