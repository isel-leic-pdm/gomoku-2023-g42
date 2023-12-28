package com.example.gomoku.domain
sealed class IOState < out T>

object Idle: IOState<Nothing>()
object  Loading: IOState<Nothing>()
data class Loaded <T> (val result: Result<T>) : IOState<T>()