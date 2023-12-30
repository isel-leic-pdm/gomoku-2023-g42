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
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.lobby.LobbyScreen
import com.example.gomoku.lobby.LobbyScreenViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
                Log.d("HomeToLobbyActivity", "Success! LobbyInfo: $it")
                if (it is Loaded) {
                    Log.d("HomeToLobbyActivity", "Success! LobbyInfo: ${it.result.getOrNull()}")
                    val result = it.result.getOrNull()
                    if (result != null) {
                        LobbyToGameActivity.navigateTo(this@HomeToLobbyActivity, result.id)
                    } else {
                        vm.waitForPlayer(app.lobbyService, app.userInfoRepository.getUserInfo())
                    }
                }
            }
        }
        setContent {
            val error by vm.error.collectAsState(initial = Idle)
            LobbyScreen(
                onCreateLobby = { vm.createLobby(app.lobbyService, app.userInfoRepository) },
                error,
                onDismiss = { vm.resetError() }
            )
        }
    }
}