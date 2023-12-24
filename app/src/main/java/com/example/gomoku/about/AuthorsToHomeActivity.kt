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
import com.example.gomoku.home.HomeScreen
import com.example.gomoku.home.HomeScreenViewModel
import com.example.gomoku.http.DependenciesContainer
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.user.User
import kotlinx.coroutines.launch

class AuthorsToHomeActivity : ComponentActivity() {
    private val vm by viewModels<HomeScreenViewModel> {
        HomeScreenViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, AuthorsToHomeActivity::class.java)
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
                getUser = {vm.fetchUserInfo()},

                onAuthorsRequested = { HomeToAuthorActivity.navigateTo(this) },
                onRankingsRequested = { HomeToRankingsActivity.navigateTo(this) }
            )

        }
    }


}