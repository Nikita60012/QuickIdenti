package com.example.quickidenti.utils

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.quickidenti.api.Utils
import com.example.quickidenti.app.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

fun checkConnection(): Boolean{
    val check = mutableStateOf(false)
    val checkApi = retrofit.create(Utils::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        try {
            check.value = checkApi.check()
        }catch (e: SocketTimeoutException){
            check.value = false
            Log.i("error", "server not found")
        }
    }
    Thread.sleep(200)
    return check.value
}