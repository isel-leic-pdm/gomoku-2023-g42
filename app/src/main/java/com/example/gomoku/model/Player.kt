package com.example.gomoku.model

enum class Player(val string: String) {
    W("W"),
    B("B");
}

fun String.toPlayer(): Player {
    return if (this == "W") Player.W
    else Player.B
}