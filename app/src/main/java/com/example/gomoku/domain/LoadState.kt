package com.example.gomoku.domain



sealed class LoadState

object Idle : LoadState()
object Loading : LoadState()
data class Loaded <T> (val result: Result<T>) : LoadState()