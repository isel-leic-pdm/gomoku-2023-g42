

package com.example.gomoku.rankings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import kotlinx.coroutines.launch

class RankingScreenViewModel : ViewModel() {

    var rankings by mutableStateOf<LoadState>(Loading)
        private set

    fun fetchRanking(service: RankingService) {
        viewModelScope.launch {
            rankings = Loading
            rankings = Loaded(runCatching { service.fetchRankings()})
        }
    }
}