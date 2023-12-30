package com.example.gomoku.model

import com.example.gomoku.game.GameModel
import com.example.gomoku.user.LoggedUser
import com.google.gson.internal.LinkedTreeMap

data class SirenMapToModel(val properties: Map<String, Any>) {

    fun toGame(): GameModel {
        val properties = this.properties
        val id = (properties["id"] as Double).toInt()
        val state = properties["state"] as String
        val playerB = (properties["playerB"] as Double).toInt()
        val playerW = (properties["playerW"] as Double).toInt()
        val boardSize = (properties["boardSize"] as Double).toInt()
        val boardMap = properties["board"] as LinkedTreeMap<*, *>
        val rules = boardMap["rules"] as String
        val variant = boardMap["variant"] as String
        val turn = (boardMap["turn"] as String).toPlayer()
        val moves = boardMap["moves"] as LinkedTreeMap<*, *>
        val resultMoves = moves.entries.associate { entry ->
            val position = (entry.key).toString().toPosition(boardSize)
            val player = (entry.value).toString().toPlayer()
            position to player
        }
        val board = BoardRun(resultMoves, boardSize, rules, variant, turn)
        return GameModel(
            id,
            board,
            state,
            playerB,
            playerW,
            boardSize
        )
    }

    fun toLoggedUser(username: String): LoggedUser {
        val properties = this.properties
        val token = properties["token"] as String
        return LoggedUser(username, token)
    }

    fun toUser(): String {
        val properties = this.properties
        return  properties["username"] as String

    }
}