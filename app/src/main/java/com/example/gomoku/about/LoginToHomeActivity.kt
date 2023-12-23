package com.example.gomoku.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.gomoku.home.HomeScreen
import com.example.gomoku.home.HomeViewModel
import com.example.gomoku.http.DependenciesContainer
import com.example.gomoku.http.MenuApplication
import com.example.gomoku.user.User
import kotlinx.coroutines.launch

class LoginToHomeActivity : ComponentActivity() {

    private val app by lazy { application as MenuApplication }
    lateinit var user: User

    private val vm by viewModels<HomeViewModel> {
        HomeScreenViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginToHomeActivity::class.java)
            origin.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
                user = app.userInfoRepository.getUserInfo()
                user.token
        }

        Log.v("AboutActivity", "onCreate")

        setContent {
            HomeScreen(
                user = user ,
                onAuthorsRequested = { HomeToAuthorActivity.navigateTo(this) },
                onRankingsRequested = { HomeToRankingsActivity.navigateTo(this) }
            )

        }
    }
}
