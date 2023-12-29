package com.example.gomoku.domain

data class PlayerRank(
    val username: String,
    val rank: String,
    val playedGames: String,
    val wonGames: String,
    val lostGames: String
)