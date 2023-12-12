package com.example.gomoku.domain

sealed class User (open val token: String )

data class NoUser(val error: String) : User(error)

data class LoggedUser(val username: String, override val token: String) : User(token)