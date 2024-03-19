package com.example.quickidenti.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.ImageButtonComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary

@Composable
fun IdentificationResultScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Row(modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .height(220.dp)
                    .width(160.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(Primary, Secondary))
                    ))
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier = Modifier
                    .height(220.dp)
                    .width(160.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(Primary, Secondary))
                    ))
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)){

                TextComponent(value = stringResource(id = R.string.identification_result), fontSize = 24)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                ImageButtonComponent(imageVector = Icons.Default.Info, description = "Worker Info") {
                    QuickIdentiAppRouter.navigateTo(Screen.PersonInfoScreen, true)
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
            ButtonComponent(value = stringResource(id = R.string.back_to_identification)) {
                QuickIdentiAppRouter.navigateTo(Screen.IdentificationScreen, false)
            }
            BackHandler(enabled = true) {
                QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
            }
        }
    }
}

@Preview
@Composable
fun IdentificationResultScreenPreview(){
    IdentificationResultScreen()
}