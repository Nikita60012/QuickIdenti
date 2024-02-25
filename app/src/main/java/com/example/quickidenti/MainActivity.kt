package com.example.quickidenti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authButton: Button = findViewById(R.id.mainAuthButton)
        authButton.setOnClickListener{
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        val regButton: Button = findViewById(R.id.mainRegButton)
        regButton.setOnClickListener{
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }
    }
}