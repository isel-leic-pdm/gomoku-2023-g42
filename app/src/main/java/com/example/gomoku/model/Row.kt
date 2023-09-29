package com.example.gomoku.model

class Row private constructor(val number: Int) {
    val index = number - 1

    companion object {
        val values = List(BOARD_DIM) { Row(it + 1) }

        operator fun invoke(number: Int) = values.first { it.number == number }
    }

    override fun toString() = "Row $number"
}

fun Int.toRowOrNull() = Row.values.find { it.number == this }
fun Int.toRow() = this.toRowOrNull() ?: throw IllegalArgumentException("Invalid row $this")

fun Int.indexToRowOrNull(): Row? = Row.values.find { this == it.index }
fun Int.indexToRow(): Row = this.indexToRowOrNull() ?: throw IllegalArgumentException("Invalid row $this")