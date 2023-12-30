package com.example.gomoku.model

enum class Player(val string: String) {
    W("W"),
    B("B");

    fun other() = if (this == W) B else W
}

fun String.toPlayer(): Player {
    return if (this == "W") Player.W
    else Player.B
}