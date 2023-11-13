package com.example.gomoku.home

enum class BoardSize{
    FIFTEEN,
    NINETEEN;

    @Override
    override fun toString(): String {
        return if (this == FIFTEEN) "15" else "19"
    }
    fun value(): Int = if (this == FIFTEEN) 15 else 19
}

enum class Rules{
    PRO,
    PRO_LONG;

    @Override
    override fun toString(): String {
        return if (this == PRO) "Pro" else "Pro Long"
    }
}

enum class Variant{
    FREESTYLE,
    SWAP;

    @Override
    override fun toString(): String {
        return if (this == FREESTYLE) "Freestyle" else "Swap"
    }

}