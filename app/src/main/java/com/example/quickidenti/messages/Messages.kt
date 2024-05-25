package com.example.quickidenti.messages

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.test.annotation.UiThreadTest
import com.example.quickidenti.R

@UiThreadTest
fun messages(context: Context, error: String){
    when(error){
        "server" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.server), Toast.LENGTH_LONG).show()
            }
        }
        "internet" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.internet), Toast.LENGTH_LONG)
                    .show()
            }
        }
        "email_already_exist" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.email_already_exist), Toast.LENGTH_LONG).show()
            }
        }
        "changes_saved" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.changes_saved), Toast.LENGTH_SHORT).show()
            }
        }
        "current_password_incorrect" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.current_password_incorrect), Toast.LENGTH_LONG).show()
            }
        }
        "new_pass_duplicate_current" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.new_pass_duplicate_current), Toast.LENGTH_LONG).show()
            }
        }
        "passwords_are_not_equals" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.passwords_are_not_equals), Toast.LENGTH_LONG).show()
            }
        }
        "entering_data_incorrect" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.entering_data_incorrect), Toast.LENGTH_LONG).show()
            }
        }
        "exit_from_app" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.exit_from_app), Toast.LENGTH_LONG).show()
            }
        }
        "email_or_pass_incorrect" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.email_or_pass_incorrect), Toast.LENGTH_LONG).show()
            }
        }
        "subscribe_bought" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.subscribe_bought), Toast.LENGTH_SHORT).show()
            }
        }
        "error_happened" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, getString(context, R.string.error_happened), Toast.LENGTH_SHORT).show()
            }
        }
    }
}