package com.example.gomoku.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.authors.AuthorScreen
import com.example.gomoku.home.HomeScreen
import com.example.gomoku.rankings.RankingApplication

class LoginToHomeActivity : ComponentActivity() {


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginToHomeActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
        setContent {
            HomeScreen(
                onAuthorsRequested = { HomeToAuthorActivity.navigateTo(this) },
                onRankingsRequested = { HomeToRankingsActivity.navigateTo(this) }
            )

        }
    }
}
