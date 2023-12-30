package com.example.gomoku.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.game.Either
import com.example.gomoku.game.GameModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.gomoku.game.Error
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.User
import kotlinx.coroutines.delay
import kotlin.runCatching


class LobbyScreenViewModel : ViewModel() {
    private val _lobbyInfoFlow: MutableStateFlow<IOState<Either<Error, GameModel?>>> =
        MutableStateFlow(Idle)
    val lobbyInfo: Flow<IOState<Either<Error, GameModel?>>>
        get() = _lobbyInfoFlow.asStateFlow()

    fun createLobby(service: LobbyService, userInfoRepository: UserInfoRepository) {
        if (_lobbyInfoFlow.value !is Idle) throw IllegalStateException("The view model is not in the idle state!")

        _lobbyInfoFlow.value = Loading
        viewModelScope.launch {
            try {
                val result = runCatching { service.createLobby(userInfoRepository.getUserInfo()) }
                _lobbyInfoFlow.value = Loaded(result)
            } catch (e: Exception) {
                //TODO(Produzir erro tal como no game)
            }

        }
    }

    fun waitForPlayer(service: LobbyService, userInfo: Pair<User, LobbyInfo>) {
        val username = (userInfo.first as LoggedUser).username
        _lobbyInfoFlow.value = Loading
        viewModelScope.launch {
            while (true) {
                delay(3000)
                val result = runCatching {
                    service.gameExists(userInfo)
                }
                if (result.getOrNull() != null && result.getOrNull() is Either.Right) {
                    _lobbyInfoFlow.value = Loaded(result)
                    break
                }
            }
        }
    }
}