package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.gomoku.domain.Loaded
import com.example.gomoku.game.Either
import com.example.gomoku.game.GameModel
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.lobby.LobbyScreen
import com.example.gomoku.lobby.LobbyScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class HomeToLobbyActivity : ComponentActivity() {

    private val vm by viewModels<LobbyScreenViewModel>()
    private val app by lazy { application as MenuApplication }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToLobbyActivity::class.java)
            origin.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm.lobbyInfo.collectLatest {
                if (it is Loaded && it.result.isSuccess ) {
                    Log.d("HomeToLobbyActivity", "Success! LobbyInfo: ${it.result.getOrNull()}")
                    LobbyToGameActivity.navigateTo(this@HomeToLobbyActivity)
                }
            }
        }
        setContent {
            LobbyScreen(onCreateLobby = { createLobby() })
        }
    }

    private fun createLobby() {
        vm.createLobby(app.lobbyService,LobbyInfo("Pro","Freestyle",15))
    }
}