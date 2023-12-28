package com.example.gomoku.lobby
import com.example.gomoku.game.Either
import com.example.gomoku.game.Error
import com.example.gomoku.game.GameModel

interface GameService{
    suspend fun getGame(username:String):Either<Error,GameModel?>
}