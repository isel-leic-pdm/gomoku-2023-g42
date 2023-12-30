package com.example.gomoku.rankings

import com.example.gomoku.domain.PlayerRank

interface RankingService{
    suspend fun  fetchRankings(): List<PlayerRank>
}