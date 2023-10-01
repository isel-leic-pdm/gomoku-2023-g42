package com.example.gomoku.model

import Cell

const val BOARD_DIM = 5


data class Board(val positions:Map<Cell,Player?>)
