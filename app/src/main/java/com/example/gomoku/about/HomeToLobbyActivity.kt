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
import com.example.gomoku.home.HomeViewModel
import com.example.gomoku.http.DependenciesContainer
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.lobby.LobbyScreen
import com.example.gomoku.lobby.LobbyScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class HomeToLobbyActivity : ComponentActivity() {

    private val vm by viewModels<LobbyScreenViewModel>()
    private val app by lazy { application as MenuApplication }
    private val vma by viewModels<HomeViewModel> {
        HomeViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToLobbyActivity::class.java)
            origin.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            while (true) {
                vm.lobbyInfo.collectLatest {
                    if (it is Loaded && it.result.getOrNull() is Either.Right) {
                        Log.d("HomeToLobbyActivity", "Success! LobbyInfo: ${it.result.getOrNull()}")
                        val result = (it.result.getOrNull() as Either.Right<GameModel?>).value
                        if (result != null) LobbyToGameActivity.navigateTo(this@HomeToLobbyActivity)
                    }
                }
            }
        }
        setContent {
            val userInfo by vma.userInfo.collectAsState(initial = Idle)
            LobbyScreen(
                onCreateLobby = ::createLobby,
                getUser = { vma.fetchUserInfo() },
                userInfo = userInfo
            )
        }
    }

    fun createLobby(token: String?) {
        vm.createLobby(app.lobbyService, LobbyInfo("Pro", "Freestyle", 15), token)
    }
}