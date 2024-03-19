package com.example.quickidenti.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.R
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.PrimarySlider
import com.example.quickidenti.ui.theme.Secondary
import com.example.quickidenti.ui.theme.SecondarySlider

@Composable
fun SubscribeScreen(){
    var slotsSliderPosition by remember{ mutableFloatStateOf(1f) }
    var daysSliderPosition by remember{ mutableFloatStateOf(1f) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Column(verticalArrangement = Arrangement.SpaceBetween){
            TextComponent(
                value = stringResource(id = R.string.subscribe_info),
                fontSize = 24)
            Spacer(modifier = Modifier.height(30.dp))
            TextComponent(
                value = "${stringResource(id = R.string.slots)}: ${slotsSliderPosition.toInt()}",
                textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(10.dp))
            Slider(
                value = slotsSliderPosition,
                onValueChange = { slotsSliderPosition = it },
                valueRange = 1f..300f,
                steps = 59,
                colors = SliderDefaults.colors(
                    thumbColor = Secondary,
                    activeTrackColor = PrimarySlider,
                    inactiveTrackColor = Primary,
                    activeTickColor = PrimarySlider,
                    inactiveTickColor = Primary
                ))
            Spacer(modifier = Modifier.height(20.dp))
            TextComponent(
                value = "${stringResource(id = R.string.days)}: ${daysSliderPosition.toInt()}",
                textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(10.dp))
            Slider(
                value = daysSliderPosition,
                onValueChange = { daysSliderPosition = it },
                valueRange = 1f..1110f,
                steps = 36,
                colors = SliderDefaults.colors(
                    thumbColor = Primary,
                    activeTrackColor = SecondarySlider,
                    inactiveTrackColor = Secondary,
                    activeTickColor = SecondarySlider,
                    inactiveTickColor = Secondary))

            Spacer(modifier = Modifier.height(30.dp))
            TextComponent(
                value = "${stringResource(id = R.string.price)}: ${slotsSliderPosition * 5 + daysSliderPosition * 10}  руб.",
                fontSize = 20)
            Spacer(modifier = Modifier.height(30.dp))
            ButtonComponent(value = stringResource(id = R.string.buy)) {
            }
        }
    }
    BackHandler(enabled = true) {
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun SubscribeScreenPreview(){
    SubscribeScreen()
}