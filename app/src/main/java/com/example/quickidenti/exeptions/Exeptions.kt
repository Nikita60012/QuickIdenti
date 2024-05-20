package com.example.quickidenti.exeptions

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.test.annotation.UiThreadTest

@UiThreadTest
fun exeptions(context: Context, error: String){
    when(error){
        "server" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Набор закрыт", Toast.LENGTH_LONG).show()
            }
        }
        "internet" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Отсутствует подключение к интернету", Toast.LENGTH_LONG)
                    .show()
            }
        }
        "applicant_not_found" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Абитуриент не найден", Toast.LENGTH_LONG).show()
            }
        }
        "havent_snils" ->{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Вы не ввели СНИЛС", Toast.LENGTH_LONG).show()
            }
        }
    }
}