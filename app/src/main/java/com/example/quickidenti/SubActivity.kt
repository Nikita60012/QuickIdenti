package com.example.quickidenti

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener

class SubActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        val slotsBar: SeekBar = findViewById(R.id.subSlotsSeekBar)
        val slotsTextEditor: EditText = findViewById(R.id.subSlotsEditTextNumber)
        slotsBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                slotsTextEditor.setText(slotsBar.progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        slotsTextEditor.onFocusChangeListener =
            OnFocusChangeListener { _, _ -> slotsBar.progress = slotsTextEditor.text.toString().toInt() }

        val daysBar: SeekBar = findViewById(R.id.subDaysSeekBar)
        val daysTextEditor: EditText = findViewById(R.id.subDaysEditTextNumber)
        daysBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               daysTextEditor.setText(daysBar.progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        daysTextEditor.onFocusChangeListener =
            OnFocusChangeListener { _, _ -> daysBar.progress = daysTextEditor.text.toString().toInt() }
        }
    }
