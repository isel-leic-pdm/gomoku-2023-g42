package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.rankings.RankingScreen
import com.example.gomoku.rankings.RankingScreenViewModel

class HomeToRankingsActivity : ComponentActivity() {

    private val viewModel by viewModels<RankingScreenViewModel>()
    private val app by lazy { application as MenuApplication }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToRankingsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")

        setContent {
            RankingScreen(
                onFetch = {viewModel.fetchRanking(app.rankingService)},
                rankings = viewModel.rankings
            ) { RankingsToHomeActivity.navigateTo(this) }
        }
    }
}




