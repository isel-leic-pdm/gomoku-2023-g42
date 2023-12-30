package com.example.gomoku.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.game.GameModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.user.User
import com.google.gson.JsonParser
import kotlinx.coroutines.delay
import kotlin.runCatching

class LobbyScreenViewModel : ViewModel() {
    private val _lobbyInfoFlow: MutableStateFlow<IOState<GameModel?>> =
        MutableStateFlow(Idle)
    private val _errorFlow: MutableStateFlow<IOState<String>> = MutableStateFlow(Idle)

    val lobbyInfo: Flow<IOState<GameModel?>>
        get() = _lobbyInfoFlow.asStateFlow()
    val error: Flow<IOState<String>>
        get() = _errorFlow.asStateFlow()

    fun createLobby(service: LobbyService, userInfoRepository: UserInfoRepository) {
        if (_lobbyInfoFlow.value !is Idle) throw IllegalStateException("The view model is not in the idle state!")

        _lobbyInfoFlow.value = Loading
        viewModelScope.launch {
            val result = runCatching { service.createLobby(userInfoRepository.getUserInfo()) }
            if (result.isSuccess) _lobbyInfoFlow.value = Loaded(result)
            else {
                _lobbyInfoFlow.value = Idle
                val jsonError = result.exceptionOrNull()?.message ?: "Unknown error"
                val msgParsed = JsonParser.parseString(jsonError).asJsonObject
                val msg = msgParsed.getAsJsonPrimitive("error").asString
                _errorFlow.value = Loaded(Result.success(msg))
            }
        }
    }

    fun waitForPlayer(service: LobbyService, userInfo: Pair<User, LobbyInfo>) {
        _lobbyInfoFlow.value = Loading
        viewModelScope.launch {
            while (true) {
                delay(3000)
                val result = runCatching {
                    service.gameExists(userInfo)
                }
                if (result.getOrNull() != null) {
                    _lobbyInfoFlow.value = Loaded(result)
                    break
                }
            }
        }
    }

    fun resetError() {
        _errorFlow.value = Idle
    }
}