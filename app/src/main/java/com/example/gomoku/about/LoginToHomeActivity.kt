/*package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.about.HomeToAuthorActivity
import com.example.gomoku.http.LoginRequest
import com.example.gomoku.login.LoginView
import com.google.gson.Gson
import okhttp3.OkHttpClient

class LoginToHomeActivity : ComponentActivity() {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val loginRequest = LoginRequest(client, gson)

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToAuthorActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
        setContent {
            LoginView(
                onInfoRequested = { loginRequest() }
            )
        }
    }

}*/