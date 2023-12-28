package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loading
import com.example.gomoku.game.GameScreen
import com.example.gomoku.game.GameScreenViewModel
import com.example.gomoku.http.MenuApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LobbyToGameActivity : ComponentActivity() {

    private val app by lazy { application as MenuApplication }
    private val vm by viewModels<GameScreenViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity/*,gameModel: GameModel*/) {
            val intent = Intent(origin, LobbyToGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm.gameInfo.collectLatest {
                while (true){
                    delay(5000)
                    if(it !is Loading) vm.getGameInfo(app.gameService,app.userInfoRepository)
                }
            }
        }

        setContent {
            val gameInfo by vm.gameInfo.collectAsState(initial = Idle)
            GameScreen(gameInfo)
        }
    }

}