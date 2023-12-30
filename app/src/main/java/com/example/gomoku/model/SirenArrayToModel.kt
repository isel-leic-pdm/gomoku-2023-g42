package com.example.gomoku.model

import com.example.gomoku.domain.PlayerRank

data class SirenArrayToModel(
    val properties: Array<Map<String, String>>
) {

    fun toRankList(): List<PlayerRank> {
        val properties = this.properties
        var counter = 0
        return properties.map { player ->
            PlayerRank(
                (++counter).toString(),
                username = player["username"] ?: "",
                points = player["rank"] ?: "",
                playedGames = player["playedGames"] ?: "",
                wonGames = player["wonGames"] ?: "",
                lostGames = player["lostGames"] ?: ""
            )

        }
    }
}