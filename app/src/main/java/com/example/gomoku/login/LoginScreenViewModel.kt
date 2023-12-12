package com.example.gomoku.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.rankings.RankingService
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {

    var user by mutableStateOf<IOState<Any>>(Idle)
        private set

    fun postUser(service: LoginService, username: String, password: String) {

        viewModelScope.launch {
            user = Loading
            user = Loaded(
            runCatching { service.postLogin(username, password) })

        }

    }

    fun setIdle(){
        user = Idle
    }
}