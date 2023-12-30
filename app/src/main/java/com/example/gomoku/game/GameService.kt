package com.example.gomoku.game

import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.User

interface GameService {
    suspend fun getGame(userInfoRepository: Pair<User, LobbyInfo>): GameModel

    suspend fun getGameById(id: Int): GameModel

    suspend fun play(
        userInfoRepository: Pair<User, LobbyInfo>,
        row: Int,
        col: Int,
        id: Int
    ): GameModel

    suspend fun forfeit(userInfoRepository: Pair<User, LobbyInfo>, id: Int)
    suspend fun getUserById(id: String) : String
}