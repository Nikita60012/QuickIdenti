package com.example.quickidenti

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.quickidenti.app.QuickIdentiApp

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            QuickIdentiApp()
        }
//        setContentView(R.layout.activity_main)
//
//        val authButton: Button = findViewById(R.id.mainAuthButton)
//        authButton.setOnClickListener{
//            val intent = Intent(this, AuthActivity::class.java)
//            startActivity(intent)
//        }
//
//        val regButton: Button = findViewById(R.id.mainRegButton)
//        regButton.setOnClickListener{
//            val intent = Intent(this, RegActivity::class.java)
//            startActivity(intent)
//        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    MainActivity()
}