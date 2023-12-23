package com.example.gomoku.lobby
import com.example.gomoku.game.Either
import com.example.gomoku.game.Error
import com.example.gomoku.game.GameModel

interface LobbyService{

    suspend fun  createLobby(lobby:LobbyInfo): Either<Error, GameModel?>


}