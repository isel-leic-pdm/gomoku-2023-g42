package com.example.gomoku.http


import android.app.Application
import com.example.gomoku.login.LoginService
import com.example.gomoku.rankings.RankingService
import com.example.gomoku.signUp.SignUpService
import com.google.gson.Gson
import okhttp3.OkHttpClient

const val LOCALHOST = "833c-2001-8a0-612f-f500-edb5-c74a-f216-f45f.ngrok-free.app"
class MenuApplication : Application() {

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    private val gson: Gson = Gson()

    //val rankingService: RankingService = NoOpRankService
    val rankingService: RankingService = RankingRequest(httpClient, gson)
    val loginService: LoginService = LoginRequest(httpClient, gson)
    val signUpService: SignUpService = SignUpRequest(httpClient, gson)

}