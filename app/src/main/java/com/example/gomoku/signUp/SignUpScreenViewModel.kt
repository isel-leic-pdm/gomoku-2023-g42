package com.example.gomoku.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import kotlinx.coroutines.launch

class SignUpScreenViewModel : ViewModel() {

    var user by mutableStateOf<IOState<Any>>(Idle)
        private set

    fun createUser(service: SignUpService, username: String, password: String) {
        viewModelScope.launch {
            user = Loading
            user = Loaded(runCatching { service.createUser(username, password)})
        }
    }

    fun setIdle() {
        user = Idle
    }
}
