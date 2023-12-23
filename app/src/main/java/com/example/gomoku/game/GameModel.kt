package com.example.gomoku.game

import com.example.demo.domain.Board

data class GameModel(
    val id: Int,
    val board: Board,
    val state: String,
    val playerB: Int,
    val playerW: Int,
    val boardSize: Int
)

data class Error(
    val error: String
)