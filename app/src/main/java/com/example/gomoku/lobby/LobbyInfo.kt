package com.example.gomoku.lobby

import com.example.gomoku.model.BoardSize
import com.example.gomoku.model.Rules
import com.example.gomoku.model.Variant

data class LobbyInfo(
    val rules:String,
    val variant:String,
    val boardSize: String
) {
    constructor() : this(Rules.values()[0].string(), Variant.values()[0].string(), BoardSize.values()[0].size.toString())
}