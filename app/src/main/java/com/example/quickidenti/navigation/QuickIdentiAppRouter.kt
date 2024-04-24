package com.example.quickidenti.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

sealed class Screen{

    data object SignInScreen: Screen()
    data object SignUpScreen: Screen()
    data object InfoScreen: Screen()
    data object SubscribeScreen: Screen()
    data object IdentificationScreen: Screen()
    data object IdentificationResultScreen: Screen()
    data object PeopleListScreen: Screen()
    data object PersonInfoScreen: Screen()
}

object QuickIdentiAppRouter{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignInScreen)
    var historyScreenList: MutableList<Screen> = mutableListOf()
    var previewScreen: MutableState<Screen> = mutableStateOf(Screen.SignInScreen)
    var lastHistoryIndex: MutableState<Int> = mutableIntStateOf(-1)
    fun navigateTo(destination: Screen, nextOrBack: Boolean){
        if(nextOrBack){
            previewScreen.value = currentScreen.value
            historyScreenList.add(currentScreen.value)
            lastHistoryIndex.value++
        }else{
            if(historyScreenList.isNotEmpty()) {historyScreenList.removeAt(lastHistoryIndex.value)}
            if (lastHistoryIndex.value != 0){lastHistoryIndex.value--}
        }
        currentScreen.value = destination
    }

}