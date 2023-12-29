package com.example.gomoku.model

import com.example.demo.domain.Board
import com.example.gomoku.game.GameModel

data class SirenMapToModel(
    val properties: Map<String, Any>
) {

    fun toGame(): GameModel {
        val properties = this.properties
        val id = properties["id"] as Int
        val state = properties["state"] as String
        val playerB = properties["playerB"] as Int
        val playerW = properties["playerW"] as Int
        val boardSize = properties["boardSize"] as Int
        val boardString = properties["board"] as String
        val board = Board.fromString(boardString)
        return GameModel(
            id,
            board,
            state,
            playerB,
            playerW,
            boardSize
        )
    }
}