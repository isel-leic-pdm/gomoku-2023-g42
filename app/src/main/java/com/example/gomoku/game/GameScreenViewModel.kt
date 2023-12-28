package com.example.gomoku.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.lobby.LobbyService
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameScreenViewModel : ViewModel() {
    private val _gameInfoFlow: MutableStateFlow<IOState<Either<Error, GameModel?>>> =
        MutableStateFlow(Idle)

    val gameInfo: Flow<IOState<Either<Error, GameModel?>>>
        get() = _gameInfoFlow.asStateFlow()

    fun setGameInfo(gameModel: GameModel) {
        _gameInfoFlow.value = Loading
        _gameInfoFlow.value = Loaded(runCatching { Either.Right(gameModel) })
    }

    fun getGameInfo(service: GameService, userInfoRepository: UserInfoRepository) {
        //if (_gameInfoFlow.value !is Idle) throw IllegalStateException("The view model is not in the idle state!")

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
        gameInfo: Flow<IOState<Either<Error, GameModel?>>>,

        ) {
        val temp = _gameInfoFlow.value
        _gameInfoFlow.value = Loading
        viewModelScope.launch {
            gameInfo.collectLatest {
                if (it is Loaded && it.result.getOrNull() is Either.Right) {
                    (it.result.getOrNull() as Either.Right).value?.board
                    val result =
                        runCatching { service.play(userInfoRepository, row, col, id) }
                    _gameInfoFlow.value = Loaded(result)
                }
            }
        }
    }

    fun waitTurn(service: LobbyService, userInfo: Pair<User, LobbyInfo>) {
        val username = (userInfo.first as LoggedUser).username
        _gameInfoFlow.value = Loading
        viewModelScope.launch {
            while (true) {
                val result = runCatching {
                    service.gameExists(userInfo)
                }
                if (result.getOrNull() != null && result.getOrNull() is Either.Right) {
                    if ((result.getOrNull() as Either.Right<GameModel?>).value?.board?.moves !=
                        (_gameInfoFlow.value as Loaded<Either.Right<GameModel?>>).result.getOrNull()?.value?.board?.moves
                    )
                        _gameInfoFlow.value = Loaded(result)
                    break
                }
            }
        }
    }
}