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
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginScreenViewModel: ViewModel() {

    var user by mutableStateOf<IOState<Any>>(Idle)
        private set

    fun loginUser(service: LoginService, userInfoRepository: UserInfoRepository, username: String, password: String) {

        viewModelScope.launch {
            user = Loading
            user = Loaded(runCatching { service.postLogin(username, password)})
            val logged : User = (user as Loaded).result.getOrNull() as User

            if (logged is LoggedUser) runBlocking { userInfoRepository.updateUserInfo(logged, null) }

        }
    }

    fun setIdle(){
        user = Idle
    }
}