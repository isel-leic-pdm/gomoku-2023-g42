package com.example.gomoku.model


class Row private constructor(val number: Int) {
    val index get() = number - 1

    companion object {
        lateinit var values: List<Row>

        operator fun invoke(number: Int) = values.first { it.number == number }
    }

    override fun toString() = "Row $number"

    class Factory(private val boardSize: Int) {
        fun createRows(): List<Row> {
            values = List(boardSize) { Row(it + 1) } + Row(-1)
            return values
        }
    }
}

fun Int.toRowOrNull() = Row.values.find { it.number == this }
fun Int.toRow() = this.toRowOrNull() ?: Row(-1)