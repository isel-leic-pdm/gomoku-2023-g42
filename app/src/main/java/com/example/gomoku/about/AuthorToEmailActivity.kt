package com.example.gomoku.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.AuthorScreen

class AuthorToEmailActivity : ComponentActivity() {
    private val url = Uri.parse("https://outlook.office.com/mail/")

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, AuthorToEmailActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
        setContent{
            AuthorScreen(
                onInfoRequested = { openUrl(url) }
            )
        }
    }

    private fun openUrl(url : Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }catch (e : ActivityNotFoundException){
            Log.e("AboutActivity", "No activity found to handle $url")
        }
    }
}

