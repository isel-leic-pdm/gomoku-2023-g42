package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.gomoku.domain.Idle
import com.example.gomoku.game.GameScreen
import com.example.gomoku.game.GameScreenViewModel

class LobbyToGameActivity : ComponentActivity() {

    private val vm by lazy { GameScreenViewModel() }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LobbyToGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gameInfo by vm.gameInfo.collectAsState(initial = Idle)
            GameScreen(
                gameInfo
            )
        }
    }
}