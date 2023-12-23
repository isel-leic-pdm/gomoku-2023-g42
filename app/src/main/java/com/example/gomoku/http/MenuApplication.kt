package com.example.gomoku.http


import android.app.Application

import com.example.gomoku.login.LoginService
import com.example.gomoku.rankings.RankingService
import com.example.gomoku.signUp.SignUpService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.gomoku.infrastructure.UserInfoDataStore
import com.example.gomoku.infrastructure.UserInfoRepository


interface DependenciesContainer {
    val userInfoRepository: UserInfoRepository
}

const val LOCALHOST = "2912-2001-8a0-612f-f500-bc8f-5ac7-6cdd-8635.ngrok-free.app"

class MenuApplication : Application(), DependenciesContainer {

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    private val gson: Gson = Gson()

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")
    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

    //val rankingService: RankingService = NoOpRankService
    val rankingService: RankingService = RankingRequest(httpClient, gson)
    val loginService: LoginService = LoginRequest(httpClient, gson)
    val signUpService: SignUpService = SignUpRequest(httpClient, gson)


}