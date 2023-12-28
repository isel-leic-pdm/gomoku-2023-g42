package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.demo.domain.BoardRun
import com.example.demo.domain.BoardWin
import com.example.gomoku.domain.Loaded
import com.example.gomoku.game.Either
import com.example.gomoku.game.GameModel
import com.example.gomoku.game.GameScreenViewModel
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.lobby.LobbyScreen
import com.example.gomoku.lobby.LobbyScreenViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeToLobbyActivity : ComponentActivity() {

    private val vm by viewModels<LobbyScreenViewModel>()
    private val vmg by viewModels<GameScreenViewModel>()
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
                vm.lobbyInfo.collectLatest { it ->
                    Log.d("HomeToLobbyActivity", "Success! LobbyInfo: $it")
                    if (it is Loaded && it.result.getOrNull() is Either.Right) {
                        Log.d("HomeToLobbyActivity", "Success! LobbyInfo: ${it.result.getOrNull()}")
                        val result = (it.result.getOrNull() as Either.Right<GameModel?>).value
                        if (result != null){
                            LobbyToGameActivity.navigateTo(this@HomeToLobbyActivity/*,result*/)
                        }
                        else {
                            vm.waitForPlayer(app.lobbyService, app.userInfoRepository.getUserInfo())
                        }
                    }
                }
        }
        setContent {
            LobbyScreen(
                onCreateLobby = ::createLobby,
            )
        }
    }

    private fun createLobby() {
        vm.createLobby(app.lobbyService, app.userInfoRepository)
    }
}