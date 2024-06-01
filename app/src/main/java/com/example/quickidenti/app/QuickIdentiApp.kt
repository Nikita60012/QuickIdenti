package com.example.quickidenti.app

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.quickidenti.R
import com.example.quickidenti.navigation.AppBar
import com.example.quickidenti.navigation.DrawerBody
import com.example.quickidenti.navigation.DrawerHeaderNav
import com.example.quickidenti.navigation.MenuItem
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.screens.IdentificationResultScreen
import com.example.quickidenti.screens.IdentificationScreen
import com.example.quickidenti.screens.PeopleListScreen
import com.example.quickidenti.screens.PersonInfoScreen
import com.example.quickidenti.screens.ProfileScreen
import com.example.quickidenti.screens.SignInScreen
import com.example.quickidenti.screens.SignUpScreen
import com.example.quickidenti.screens.SubscribeScreen
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient().newBuilder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()
val retrofit = Retrofit.Builder()
    .baseUrl("http:/192.168.0.106:8000")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
   .build()
val token = mutableStateOf("empty")
val humanId = mutableIntStateOf(-1)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuickIdentiApp() {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeaderNav()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "identification",
                        title = stringResource(id = R.string.identification),
                        contentDescription = "Identification",
                        icon = Icons.Default.PhotoCamera
                    ),
                    MenuItem(
                        id = "peopleList",
                        title = stringResource(id = R.string.people_list),
                        contentDescription = "People list",
                        icon = Icons.AutoMirrored.Filled.List
                    ),
                    MenuItem(
                        id = "profile",
                        title = stringResource(id = R.string.profile),
                        contentDescription = "Accaunt profile",
                        icon = Icons.Default.AccountCircle
                    ),
                    MenuItem(
                        id = "subscribe",
                        title = stringResource(id = R.string.subscribe),
                        contentDescription = "Subscribe",
                        icon = Icons.Default.MonetizationOn
                    ),
                    MenuItem(
                        id = "logout",
                        title = stringResource(id = R.string.logout),
                        contentDescription = "Log out",
                        icon = Icons.AutoMirrored.Filled.Logout
                    ),
                ),
                onItemClick = {
                    when(it.id){
                        "profile" -> {
                            QuickIdentiAppRouter.navigateTo(Screen.InfoScreen, true)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        "subscribe" -> {
                            QuickIdentiAppRouter.navigateTo(Screen.SubscribeScreen, true)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        "peopleList" -> {
                            QuickIdentiAppRouter.navigateTo(Screen.PeopleListScreen, true)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        "identification" -> {
                            QuickIdentiAppRouter.navigateTo(Screen.IdentificationScreen, true)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        "logout" -> {
                            QuickIdentiAppRouter.historyScreenList = mutableListOf()
                            QuickIdentiAppRouter.lastHistoryIndex = mutableIntStateOf(-1)
                            QuickIdentiAppRouter.navigateTo(Screen.SignInScreen, false)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    }
                })
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {

            Crossfade(
                targetState = QuickIdentiAppRouter.currentScreen,
                label = ""
            ) { currentState ->
                when (currentState.value) {
                    is Screen.SignInScreen -> {
                        SignInScreen()
                    }

                    is Screen.SignUpScreen -> {
                        SignUpScreen()
                    }

                    is Screen.InfoScreen -> {
                        ProfileScreen()
                    }

                    is Screen.SubscribeScreen -> {
                        SubscribeScreen()
                    }

                    is Screen.IdentificationScreen -> {
                        IdentificationScreen()
                    }

                    is Screen.PeopleListScreen -> {
                        PeopleListScreen()
                    }

                    is Screen.IdentificationResultScreen ->{
                        IdentificationResultScreen()
                    }

                    is Screen.PersonInfoScreen ->{
                        PersonInfoScreen()
                    }

                }
            }

        }
    }
}
