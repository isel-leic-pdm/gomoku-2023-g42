package com.example.gomoku.rankings



interface RankingService{

    suspend fun  fetchRankings(): String


}
object NoOpRankService : RankingService {

    override suspend fun fetchRankings(): String {

        return "TEST STRING"
    }
}
