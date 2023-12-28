package com.example.gomoku.game
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.User

interface GameService{
    suspend fun getGame(userInfoRepository: Pair<User, LobbyInfo>):Either<Error,GameModel?>

    suspend fun play(userInfoRepository: Pair<User, LobbyInfo>, row:Int, col:Int, id:Int):Either<Error,GameModel?>
}