package com.example.gomoku.rankings



sealed class LoadState

object Idle : LoadState()
object Loading : LoadState()
data class Loaded(val result: Result<String>) : LoadState()