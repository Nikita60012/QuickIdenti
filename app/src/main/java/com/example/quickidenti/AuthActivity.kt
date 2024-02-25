package com.example.quickidenti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AuthActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val authButton: Button = findViewById(R.id.authSignInButton)
        authButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}