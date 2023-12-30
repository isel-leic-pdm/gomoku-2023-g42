package com.example.gomoku.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.User
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserInfoRepository): ViewModel() {
    companion object {
        fun factory(repository: UserInfoRepository) = viewModelFactory {
            initializer { HomeViewModel(repository) }
        }
    }

    private val _userInfoFlow: MutableStateFlow<IOState<Pair<User, LobbyInfo>>> = MutableStateFlow(Idle)
    private val _errorFlow: MutableStateFlow<IOState<String>> = MutableStateFlow(Idle)

    val userInfo: StateFlow<IOState<Pair<User, LobbyInfo>>>
        get() = _userInfoFlow.asStateFlow()
    val error: Flow<IOState<String>>
        get() = _errorFlow.asStateFlow()

    fun fetchUserInfo() {
        _userInfoFlow.value = Loading
        viewModelScope.launch {
            val result = runCatching { repository.getUserInfo() }
            if(result.isSuccess) _userInfoFlow.value = Loaded(result)
            else {
                _userInfoFlow.value = Idle
                val jsonError = result.exceptionOrNull()?.message ?: "Unknown error"
                val msgParsed = JsonParser.parseString(jsonError).asJsonObject
                val msg = msgParsed.getAsJsonPrimitive("error").asString
                _errorFlow.value = Loaded(Result.success(msg))
            }
        }
    }

    fun resetError() {
        _errorFlow.value = Idle
    }
}

