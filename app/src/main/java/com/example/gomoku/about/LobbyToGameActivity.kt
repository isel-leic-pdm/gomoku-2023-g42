package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.game.Either
import com.example.gomoku.game.GameModel
import com.example.gomoku.game.GameScreen
import com.example.gomoku.game.GameScreenViewModel
import com.example.gomoku.http.MenuApplication
import kotlinx.coroutines.launch

class LobbyToGameActivity : ComponentActivity() {

    private val app by lazy { application as MenuApplication }
    private val vm by viewModels<GameScreenViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LobbyToGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm.gameInfo.collect { it ->
                if (it is Loaded && it.result.getOrNull() is Either.Right) {
                    Log.d("LobbyToGameActivity", "Success! LobbyInfo: ${it.result.getOrNull()}")
                    val result = (it.result.getOrNull() as Either.Right<GameModel?>).value
                    if (result != null){
                        //TODO(Play)
                    }

                }
                else {
                    vm.waitTurn(app.lobbyService, app.userInfoRepository.getUserInfo())
                }
            }
        }
        setContent {
            val gameInfo by vm.gameInfo.collectAsState(initial = Idle)
            GameScreen(
                gameInfo,
                ::getGame
            )
        }
    }

    private fun getGame() {
        vm.getGameInfo(app.lobbyService, app.userInfoRepository)
    }
}