package com.example.quickidenti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        val regButton: Button = findViewById(R.id.regSignUpButton)
        regButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}