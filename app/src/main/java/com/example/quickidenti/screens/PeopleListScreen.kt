package com.example.quickidenti.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen

@Composable
fun PeopleListScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        val el1 = Element(0, "nik")
        val el2 = Element(1, "nik2")
        val el3 = Element(2, "nik3")
        val el4 = Element(3, "nik3")
        val el5 = Element(4, "nik3")
        val el6 = Element(5, "nik3")
        val el7 = Element(6, "nik3")
        val el8 = Element(7, "nik3")
        val el9 = Element(8, "nik3")
        val el10 = Element(9, "nik3")
        val el11 = Element(10, "nik3")

        val elements = listOf(el1, el2, el3, el4, el5, el6, el7, el8, el9, el10, el11)
        Column{
            SimpleLazyColumnScreen(elements)
        }
    }
    BackHandler(enabled = true) {
        QuickIdentiAppRouter.navigateTo(QuickIdentiAppRouter.historyScreenList[QuickIdentiAppRouter.lastHistoryIndex.value], false)
    }
}
@Composable
fun SimpleLazyColumnScreen(element: List<Element>) {
    val elements by remember { mutableStateOf(element) }
    Box {
        LazyColumn {
            items(elements) { element ->
                PersonView(id = element.id,
                    title = element.title,
                    deleteClick = {id ->
                    Log.i("Delete", "Man delete $id")
                },
                    onItemClick = { id ->
                        Log.i("Click", "Man click $id")
                    QuickIdentiAppRouter.navigateTo(Screen.PersonInfoScreen, true)
                })
            }
        }
    }
}
@Composable
fun PersonView(id: Int,
               title: String,
               deleteClick: (Int) -> Unit,
               onItemClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .clickable {
                    onItemClick(id)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        deleteClick(id)
                    }
            )

        }
    }
}
@Preview
@Composable
fun PeopleListScreenPreview(){
    PeopleListScreen()
}

class Element(var id: Int, var title: String)