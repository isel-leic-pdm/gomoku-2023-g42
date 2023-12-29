package com.example.gomoku.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GameScreenViewModel : ViewModel() {
    private val _gameInfoFlow: MutableStateFlow<IOState<GameModel>> =
        MutableStateFlow(Idle)

    val gameInfo: Flow<IOState<GameModel>>
        get() = _gameInfoFlow.asStateFlow()

    fun getGameInfo(service: GameService, userInfoRepository: UserInfoRepository) {

        _gameInfoFlow.value = Loading
        viewModelScope.launch {
            val result =
                runCatching { service.getGame((userInfoRepository.getUserInfo())) }
            _gameInfoFlow.value = Loaded(result)
        }
    }

    fun play(
        service: GameService,
        userInfoRepository: Pair<User, LobbyInfo>,
        row: Int,
        col: Int,
        id: Int,
        ) {
        _gameInfoFlow.value = Loading
        viewModelScope.launch {
            val result =
                runCatching {
                    service.play(userInfoRepository,row,col,id)
                }
                _gameInfoFlow.value = Loaded(result)
        }
    }

}