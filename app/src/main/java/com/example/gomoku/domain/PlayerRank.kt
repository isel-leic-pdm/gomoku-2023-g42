package com.example.gomoku.domain

data class PlayerRank(
    val rank: String,
    val username: String,
    val points: String,
    val playedGames: String,
    val wonGames: String,
    val lostGames: String
)