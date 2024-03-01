package com.example.quickidenti

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class PeopleListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val us1 = User(0, "nik", 15)
        val us2 = User(1, "nik2", 14)
        val us3 = User(2, "nik3", 16)
        val users = listOf(us1, us2, us3)
        setContent {
            Column {
                SimpleLazyColumnScreen(users)
            }
//            SimpleRowScreen()
        }
    }

    @Composable
    fun SimpleColumnScreen() {
        val listA = listOf<String>(
            "Example",
            "Android",
            "Tutorial",
            "Jetpack",
            "Compose",
            "List",
            "Example",
            "Simple",
            "List"
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)
        ) {
            items(listA) { item ->
                Spacer(modifier = Modifier.height(100.dp))
                Text(text = item)
            }
        }
    }
    @Composable
    fun SimpleLazyColumnScreen(user: List<User>) {
        var users by remember { mutableStateOf(user) }
        var clickedItemId by remember { mutableStateOf(Int.MIN_VALUE) }
        Box {
            LazyColumn {
                items(users) { user ->
                    PersonView(id = user.id, name = user.name,onItemClick = { id -> clickedItemId =
                        if (clickedItemId == id) Int.MIN_VALUE
                        else id
                    },
                        expandedItemId = clickedItemId)
                }
            }
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                users = users.toMutableList().also {
                    it.add(0, User(5, "123", 123))
                }
            }, border = BorderStroke(5.dp, Color.Black), modifier = Modifier.padding(5.dp)) {
                Text(text = "Add")
            }
            Button(onClick = { users = users.drop(1) }) {
                Text(text = "Remove")
            }
            Button(onClick = {})
            {
                Text(text = "Shuffle")
            }
        }
        }
    }
    @Composable
    fun PersonView(id: Int,
                   name: String,
                   onItemClick: (Int) -> Unit,
                   expandedItemId: Int) {
        val rotation = animateFloatAsState(targetValue = if (id == expandedItemId) 180f else 0f,
            label = ""
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = name,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .align(CenterVertically)
                        .graphicsLayer(
                            rotationZ = rotation.value
                        )
                        .clickable { onItemClick(id) }
                )
            }
            AnimatedVisibility(visible = id == expandedItemId) {
                Text(
                    text = "Additional text",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }

    @Composable
    fun SimpleRowScreen() {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(100) { value ->
                Text(text = value.toString())
            }
        }
    }


//class PeopleListActivity: ListFragment() {
//    var data: Array<String> = arrayOf("kotlin", "jave", "C++")
//

}
class User(var id: Int, var name: String, var age: Int){}