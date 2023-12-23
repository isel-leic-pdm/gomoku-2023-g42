package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.gomoku.domain.Loaded
import com.example.gomoku.lobby.LobbyScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class HomeToLobbyActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToLobbyActivity::class.java)
            origin.startActivity(intent)
        }
    }

    private val vm by viewModels<LobbyScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm.lobbyInfo.collectLatest {
                if (it is Loaded && it.result.isSuccess) {
                    /*TODO*/
                }
            }
        }
        setContent {
            
        }
    }
    /*private fun doNavigation(userInfo: UserInfo?) {
        if (userInfo == null)
            UserPreferencesActivity.navigateTo(this)
        else
            LobbyActivity.navigateTo(this)
    }*/

}