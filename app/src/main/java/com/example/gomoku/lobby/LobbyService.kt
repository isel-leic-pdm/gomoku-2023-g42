package com.example.gomoku.lobby

import com.example.gomoku.game.GameModel
import com.example.gomoku.user.User

interface LobbyService {
    suspend fun createLobby(userInfoRepository: Pair<User, LobbyInfo>): GameModel?

    suspend fun gameExists(userInfoRepository: Pair<User, LobbyInfo>): GameModel
}