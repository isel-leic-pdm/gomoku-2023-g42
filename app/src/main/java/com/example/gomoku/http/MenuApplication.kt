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
import com.example.gomoku.lobby.LobbyService


interface DependenciesContainer {
    val userInfoRepository: UserInfoRepository
}

const val LOCALHOST = "5d14-2001-8a0-6c99-c800-4cc6-5de5-c30e-a18f.ngrok-free.app"

class MenuApplication : Application(), DependenciesContainer {

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    private val gson: Gson = Gson()

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")
    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

    val rankingService: RankingService = RankingRequest(httpClient, gson)
    val loginService: LoginService = LoginRequest(httpClient, gson)
    val signUpService: SignUpService = SignUpRequest(httpClient, gson)
    val lobbyService :LobbyService = LobbyRequest(httpClient,gson)


}