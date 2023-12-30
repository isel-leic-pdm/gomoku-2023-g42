package com.example.gomoku.user

sealed class User (open val token: String )

data class NoUser(val error: String) : User(error)

data class LoggedUser(val username: String, override val token: String/*, val playerId: Int*/): User(token)