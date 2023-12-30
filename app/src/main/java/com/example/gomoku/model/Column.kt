package com.example.gomoku.model


class Column private constructor(val symbol: Char) {
    val index get() =  symbol - 'A'

    companion object {
        lateinit var values: List<Column>

        operator fun invoke(symbol: Char) = values.first { it.symbol == symbol }
    }

    override fun toString() = "Column $symbol"

    class Factory(private val boardSize: Int){
        fun createColumns(): List<Column>  {
            values = List(boardSize) { Column('A' + it) } + Column('?')
            return values
        }
    }
}

fun Char.toColumnOrNull() = Column.values.find { it.symbol == this }
fun Char.toColumn() = this.toColumnOrNull() ?: Column('?')
