package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.domain.Loaded
import com.example.gomoku.game.GameScreenViewModel
import com.example.gomoku.game.GameView

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
            GameView(
                vm.gameInfo as Loaded<*>
            )
        }
    }
}