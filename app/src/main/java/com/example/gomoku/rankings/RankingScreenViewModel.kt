

package com.example.gomoku.rankings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading


import kotlinx.coroutines.launch

class RankingScreenViewModel : ViewModel() {

    var rankings by mutableStateOf<IOState<Any>>(Idle)
        private set

    fun fetchRanking(service: RankingService) {
        viewModelScope.launch {
            rankings = Loading
            rankings = Loaded(runCatching { service.fetchRankings()})
        }
    }
}