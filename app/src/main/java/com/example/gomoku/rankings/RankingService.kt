package com.example.gomoku.rankings

import com.example.gomoku.domain.PlayerRank


interface RankingService{

    suspend fun  fetchRankings(): List<PlayerRank>


}
object NoOpRankService : RankingService {

    override suspend fun fetchRankings(): List<PlayerRank> {

        return listOf(
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),
            PlayerRank("test", "1", "0", "0", "0"),


        )
    }
}
