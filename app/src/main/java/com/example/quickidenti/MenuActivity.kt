package com.example.quickidenti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val subscriptionButton: Button = findViewById(R.id.menuSubscriptionButton)
        subscriptionButton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
        val listButton: Button = findViewById(R.id.menuListButton)
        listButton.setOnClickListener{
            val intent = Intent(this, PeopleListActivity::class.java)
            startActivity(intent)
        }
        val identificationButton: Button = findViewById(R.id.menuIdentificationButton)
        identificationButton.setOnClickListener{
            val intent = Intent(this, IdentificationActivity::class.java)
            startActivity(intent)
        }
    }
}