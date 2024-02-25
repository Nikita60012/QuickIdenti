package com.example.quickidenti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class IdentificationResultActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onBackPressedDispatcher
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val backToMenuButton: Button = findViewById(R.id.resultBackToMenuButton)
        backToMenuButton.setOnClickListener{
            finish()
        }
    }
}