package com.example.quickidenti.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickidenti.api.People
import com.example.quickidenti.app.humanId
import com.example.quickidenti.app.retrofit
import com.example.quickidenti.app.token
import com.example.quickidenti.components.LoadingComponent
import com.example.quickidenti.components.SimpleLazyColumnScreen
import com.example.quickidenti.dto.People.PeopleList
import com.example.quickidenti.messages.messages
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.net.SocketTimeoutException


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PeopleListScreen(){
    val peoples = remember{ mutableListOf<PeopleList>()}
    val dataReceived = remember{ mutableStateOf(false)}
    val context = LocalContext.current
    val humanDeleted = remember { mutableStateOf(false) }
    val listApi = retrofit.create(People::class.java)
    if(!dataReceived.value) {
        CoroutineScope(Dispatchers.IO).launch {
            val status = listApi.checkSubscribe(token.value)
            if(status.date_status) {
                try {
                    peoples.addAll(listApi.getPeoples(token.value))
                    sleep(200)
                    dataReceived.value = true
                } catch (timeOut: SocketTimeoutException) {
                    Log.i("serverError", "server not found")
                    messages(context, "server")
                }
            }else{
                Log.i("subscribe_end", "user subscribe is end")
                messages(context, "subscribe_end")
            }
        }
    }
    if(dataReceived.value) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column {
                SimpleLazyColumnScreen(peoples,
                    delete = {id ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val status = listApi.checkSubscribe(token.value)
                            if(status.date_status) {
                                humanDeleted.value = listApi.delHuman(token.value, id)
                                dataReceived.value = false
                                peoples.clear()
                            }else{
                                Log.i("subscribe_end", "user subscribe is end")
                                messages(context, "subscribe_end")
                            }
                        }
                    })
                if(humanDeleted.value){
                    messages(context, "human_deleted")
                    humanDeleted.value = false
                }
            }
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {

                FloatingActionButton(modifier = Modifier
                    .widthIn(48.dp)
                    .heightIn(48.dp),
                    shape = RoundedCornerShape(50.dp),
                    onClick = {
                        humanId.intValue = -1
                        Log.i("add_new_human", "adding new human")
                        QuickIdentiAppRouter.navigateTo(Screen.PersonInfoScreen, true)}) {
                    Box(
                        modifier = Modifier
                            .widthIn(48.dp)
                            .heightIn(48.dp)
                            .background(
                                brush = Brush.radialGradient(listOf(Secondary, Primary)),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add worker")
                    }
                }
            }
        }
    }else{
        LoadingComponent()
    }
    BackHandler(enabled = true) {
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun PeopleListScreenPreview(){
    PeopleListScreen()
}
