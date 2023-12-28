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
import kotlinx.coroutines.launch

class LobbyToGameActivity : ComponentActivity() {

    private val app by lazy { application as MenuApplication }
    private val vm by viewModels<GameScreenViewModel>()



    companion object {
        fun navigateTo(origin: ComponentActivity/*,gameModel: GameModel*/) {
            val intent = Intent(origin, HomeToLobbyActivity::class.java)
            origin.startActivity(intent)
        }

        /*private fun createIntent(ca:ComponentActivity,gameModel: GameModel):Intent{
            val intent = Intent(ca,LobbyToGameActivity::class.java)
            intent.putExtra("boardSize",gameModel.boardSize)
            intent.putExtra("id",gameModel.id)
            intent.putExtra("playerB",gameModel.playerB)
            intent.putExtra("playerW",gameModel.playerW)
            intent.putExtra("state",gameModel.state)
            intent.putExtra("rules",gameModel.board.rules)
            intent.putExtra("variant",gameModel.board.variant)
            return intent
        }*/
    }

    //1-Ir buscar o board de inicio à db para puder desenhar
    //2-Esperar pela jogada do adversario para puder atualizar o meu board
    override fun onCreate(savedInstanceState: Bundle?) {
        /*val boardSize = intent.getIntExtra("boardSize", 15)
        val id = intent.getIntExtra("boardSize", -1)
        val playerB = intent.getIntExtra("playerB", -1)
        val playerW = intent.getIntExtra("playerW", -1)
        val state = intent.getStringExtra("state")
        val rules = intent.getStringExtra("rules")
        val variant = intent.getStringExtra("variant")

        val board = BoardRun(emptyMap(),boardSize,rules!!,variant!!,Player.B)
        val game = GameModel(id,board,state!!,playerB,playerW,boardSize)*/

        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm.gameInfo.collect {
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