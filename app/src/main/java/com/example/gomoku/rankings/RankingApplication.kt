package com.example.gomoku.rankings


import android.app.Application
import com.example.gomoku.http.RankingRequest
import com.google.gson.Gson
import okhttp3.OkHttpClient

class RankingApplication : Application() {

    val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    val gson: Gson = Gson()

    val rankingService: RankingService = NoOpRankService
    //  val rankingService: RankingService = RankingRequest(httpClient, gson)

}