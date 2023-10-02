package com.example.gomoku.model

import Cell

const val BOARD_DIM = 5

data class Board(val positions: Map<Cell, Player?>, val turn: Player)

//typealias Move = Pair<Cell,Player?>
fun Board.add(cell: Cell, player: Player): Board {
    require(cell != Cell.INVALID) { "This position doesn't exist" }
    return copy(positions = positions + (cell to player), turn = turn.other())
}

val emptyBoard = Board(emptyMap(), Player.WHITE)