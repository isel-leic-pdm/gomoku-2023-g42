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


class LobbyScreenViewModel : ViewModel() {
    private val _lobbyInfoFlow: MutableStateFlow<IOState<Either<Error, GameModel?>>> = MutableStateFlow(Idle)
    val lobbyInfo: Flow<IOState<Either<Error, GameModel?>>>
        get() = _lobbyInfoFlow.asStateFlow()

    fun createLobby(service: LobbyService, lobby: LobbyInfo) {
        if (_lobbyInfoFlow.value !is Idle) throw IllegalStateException("The view model is not in the idle state!")

        _lobbyInfoFlow.value = Loading
        viewModelScope.launch {
            val result = runCatching { service.createLobby(lobby) }
            _lobbyInfoFlow.value = Loaded(result)
        }
    }

}