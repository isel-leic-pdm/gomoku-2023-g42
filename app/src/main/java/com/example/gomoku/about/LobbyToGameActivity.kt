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
import com.example.gomoku.model.PlayInputModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LobbyToGameActivity : ComponentActivity() {

    private val app by lazy { application as MenuApplication }
    private val vm by viewModels<GameScreenViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity, id: Int) {
            origin.startActivity(createIntent(origin, id))
        }

        private fun createIntent(ca: ComponentActivity, gameId: Int): Intent {
            val intent = Intent(ca, LobbyToGameActivity::class.java)
            intent.putExtra("id", gameId)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val id = intent.getIntExtra("id", -1)
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            //val playerId = (app.userInfoRepository.getUserInfo().first as LoggedUser).playerId
            vm.gameInfo.collectLatest {
                while (true) {
                    delay(3000)
                    if (it !is Loading) vm.getGameInfo(app.gameService, app.userInfoRepository)
                }
            }
        }

        setContent {
            val gameInfo by vm.gameInfo.collectAsState(initial = Idle)
            val error by vm.error.collectAsState(initial = Idle)
            GameScreen(gameInfo, onPlay = { play(it,id) }, error, onDismiss = {vm.resetError()})
        }
    }

    private fun play(playInputModel: PlayInputModel, id : Int) {
        val userInfo = runBlocking {
            app.userInfoRepository.getUserInfo()
        }
        vm.play(app.gameService, userInfo, playInputModel.row, playInputModel.col, id)
    }
}