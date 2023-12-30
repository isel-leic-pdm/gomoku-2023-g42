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
import com.example.gomoku.home.HomeViewModel
import com.example.gomoku.http.DependenciesContainer
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.LoggedUser
import kotlinx.coroutines.runBlocking

class LoginToHomeActivity : ComponentActivity() {

    private val vm by viewModels<HomeViewModel> {
        HomeViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }
    private val app by lazy { application as MenuApplication }

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
            val error by vm.error.collectAsState(initial = Idle)
            HomeScreen(
                userInfo = userInfo,
                getUser = { vm.fetchUserInfo() },
                onAuthorsRequested = { HomeToAuthorActivity.navigateTo(this) },
                onRankingsRequested = { HomeToRankingsActivity.navigateTo(this) },
                onUpdateLobby = ::updateLobby,
                error = error,
                onDismiss = { vm.resetError() }
            ) { HomeToLobbyActivity.navigateTo(this) }
        }
    }

    private  fun updateLobby(rules: String, variant: String, size: String){
        val user = runBlocking { app.userInfoRepository.getUserInfo().first}
        val lobby = LobbyInfo(rules, variant, size)
        if (user is LoggedUser) runBlocking{app.userInfoRepository.updateUserInfo(user, lobby)}
    }
}
