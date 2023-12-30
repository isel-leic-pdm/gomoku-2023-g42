package com.example.gomoku.model

interface Option {
    fun string(): String
}

typealias Moves = Map<Position, Player>

enum class Rules : Option {
    PRO,
    LONG_PRO;

    override fun string(): String {
        return if (this == PRO) "Pro" else "Long Pro"
    }
}

enum class Variant : Option {
    FREESTYLE,
    SWAP;

    override fun string(): String {
        return if (this == FREESTYLE) "Freestyle" else "Swap after 1st move"
    }
}

enum class BoardSize(val size: Int) : Option {
    SMALL(15),
    BIG(19);

    override fun string() = size.toString()
}

sealed class Board(val moves: Moves, val size: Int, private val rules: String, private val variant: String) {

    /**
     * Função "equals" em que servirá para verificar se o objeto é o mesmo e efetuar a verificação de que se trata do mesmo objeto ou não.
     * @param other que representa o outro objeto com que queremos comparar esse Board com o other.
     * @return Boolean a representar se se trata do mesmo objeto ou não.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Board) return false
        if (this::class != other::class) return false
        return moves.size == other.moves.size
    }

    //Função "hashCode" que será igual ao valor do hashcode de moves.
    override fun hashCode(): Int = moves.hashCode()

    override fun toString() = when (this) {
        is BoardRun -> turn.string + size.toString() + rules + variant + '\n' + moves.toString()
        is BoardWin -> winner.string + size.toString() + rules + variant + '\n' + moves.toString()
        is BoardDraw -> moves.toString()
    }
}

/**
 * Classe "BoardRun" que representa o tabuleiro de jogo quando este está a ser jogado retornando o objeto do tipo "Board".
 * @property moves representa os moves desse board.
 * @property turn representa o turno do jogador que é a jogar ou não.
 * @return Board representa o Board que representa o nosso BoardRun.
 */
class BoardRun(moves: Moves, size: Int, rules: String, variant: String, val turn: Player) :
    Board(moves, size, rules, variant)

/**
 * Classe "BoardWin" que representa o tabuleiro de jogo quando existe um vencedor desse jogo.
 * @property moves representa os movimentos efetuados nesse tabuleiro.
 * @property winner representa o vencedor desse jogo.
 * @return Board representa o Board quando este acabou com um vencedor.
 */
class BoardWin(moves: Moves, size: Int, rules: String, variant: String, val winner: Player) :
    Board(moves, size, rules, variant)

/**
 * Classe "BoardDraw" que representa o tabuleiro de jogo quando este empatou.
 * @property moves representa os movimentos efetuados nesse tabuleiro.
 * @return Board representa o board quando este acabou em empate.
 */
class BoardDraw(moves: Moves, size: Int, rules: String, variant: String) :
    Board(moves, size, rules, variant)