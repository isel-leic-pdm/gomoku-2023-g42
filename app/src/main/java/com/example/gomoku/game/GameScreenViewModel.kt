package com.example.gomoku.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GameScreenViewModel : ViewModel() {
    private val _gameInfoFlow: MutableStateFlow<IOState<Either<Error, GameModel?>>> =
        MutableStateFlow(Idle)

    val gameInfo: Flow<IOState<Either<Error, GameModel?>>>
        get() = _gameInfoFlow.asStateFlow()

    fun setGameInfo(gameModel: GameModel) {
        viewModelScope.launch {
            _gameInfoFlow.value = Loading
            _gameInfoFlow.value = Loaded(runCatching{Either.Right(gameModel)})
        }
    }
}