package com.example.gomoku.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.User
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.Result.Companion.success

class GameScreenViewModel : ViewModel() {
    private val _gameInfoFlow: MutableStateFlow<IOState<GameModel>> = MutableStateFlow(Idle)
    private val _errorFlow: MutableStateFlow<IOState<String>> = MutableStateFlow(Idle)
    private val gameId: MutableState<Int> = mutableIntStateOf(0)

    val gameInfo: Flow<IOState<GameModel>>
        get() = _gameInfoFlow.asStateFlow()
    val error: Flow<IOState<String>>
        get() = _errorFlow.asStateFlow()

    fun getGameInfo(service: GameService, userInfoRepository: UserInfoRepository) {
        if (_gameInfoFlow.value is Loaded)
            gameId.value = (_gameInfoFlow.value as Loaded<GameModel>).result.getOrNull()?.id ?: 0
        _gameInfoFlow.value = Loading
        viewModelScope.launch {
            val result =
                runCatching {
                    if(gameId.value == 0) service.getGame((userInfoRepository.getUserInfo()))
                    else service.getGameById(gameId.value)
                }
            if (result.isSuccess) _gameInfoFlow.value = Loaded(result)
            else {
                _gameInfoFlow.value = Idle
                val jsonError = result.exceptionOrNull()?.message ?: "Unknown error"
                val msgParsed = JsonParser.parseString(jsonError).asJsonObject
                val msg = msgParsed.getAsJsonPrimitive("error").asString
                _errorFlow.value = Loaded(success(msg))
            }
        }
    }

    fun play(
        service: GameService,
        userInfoRepository: Pair<User, LobbyInfo>,
        row: Int,
        col: Int,
        id: Int
        ) {
        _gameInfoFlow.value = Loading
        viewModelScope.launch {
                val result =
                    runCatching {
                        service.play(userInfoRepository,row,col,id)
                    }
                if (result.isSuccess) _gameInfoFlow.value = Loaded(result)
                else {
                    _gameInfoFlow.value = Idle
                    val jsonError = result.exceptionOrNull()?.message ?: "Unknown error"
                    val msgParsed = JsonParser.parseString(jsonError).asJsonObject
                    val msg = msgParsed.getAsJsonPrimitive("error").asString
                    _errorFlow.value = Loaded(success(msg))
            }
        }
    }

    fun forfeit(service: GameService, userInfoRepository: Pair<User, LobbyInfo>, gameId: Int) {
        _gameInfoFlow.value = Loading
        viewModelScope.launch {
            runCatching {
                service.forfeit(userInfoRepository, gameId)
            }
        }
    }

    fun resetError() {
        _errorFlow.value = Idle
    }
}