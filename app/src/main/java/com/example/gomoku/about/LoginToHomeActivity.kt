package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.gomoku.domain.Idle
import com.example.gomoku.home.HomeScreen
import com.example.gomoku.home.HomeScreenViewModel
import com.example.gomoku.http.DependenciesContainer
import com.example.gomoku.lobby.LobbyScreenViewModel

class LoginToHomeActivity : ComponentActivity() {

    private val vm by viewModels<HomeScreenViewModel> {
        HomeScreenViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }

    private val vma by viewModels<LobbyScreenViewModel>()


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginToHomeActivity::class.java)
            origin.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        Log.v("AboutActivity", "onCreate")

        setContent {
            val userInfo by vm.userInfo.collectAsState(initial = Idle)
            HomeScreen(
                userInfo = userInfo,
                getUser = { vm.fetchUserInfo() },
                onAuthorsRequested = { HomeToAuthorActivity.navigateTo(this) },
                onRankingsRequested = { HomeToRankingsActivity.navigateTo(this) },
                onLobbyRequested = { HomeToLobbyActivity.navigateTo(this) }
            )

        }

    }
}
