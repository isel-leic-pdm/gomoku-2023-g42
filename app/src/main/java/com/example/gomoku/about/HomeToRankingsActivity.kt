package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.http.RankingRequest
import com.example.gomoku.rankings.RankingScreen
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class HomeToRankingsActivity : ComponentActivity() {

    val client = OkHttpClient()
    val gson = Gson()


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToRankingsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
        var result = "TESTE"
        setContent {


            RankingScreen(
                rankings = result,
            ) { RankingsToHomeActivity.navigateTo(this) }
        }
    }

     private suspend fun rankings(client: OkHttpClient, gson: Gson) = RankingRequest(client,gson).getRankings()


}




