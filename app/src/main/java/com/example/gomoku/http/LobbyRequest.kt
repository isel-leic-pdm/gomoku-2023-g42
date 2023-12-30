package com.example.gomoku.http

import com.example.gomoku.game.GameModel
import com.example.gomoku.lobby.LobbyService
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.model.SirenMapToModel
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.User

class LobbyRequest(private val client: OkHttpClient, private val gson: Gson) : LobbyService {

    override suspend fun createLobby(
        userInfoRepository: Pair<User, LobbyInfo>
    ): GameModel? {
        val request = requestMakerCreateLobby(
            userInfoRepository.second,
            (userInfoRepository.first as LoggedUser).token
        )

        return suspendCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    val bodyString = body?.string()
                    if (!response.isSuccessful || body == null) response.body?.let { bd ->
                        cont.resumeWithException(Exception(bodyString ?: "Unknown error"))
                    }
                    else {
                        val gson = if (bodyString != null) gson.fromJson(bodyString, SirenMapToModel::class.java) else null
                        val res = if (gson?.properties == null) null else gson.toGame()
                        cont.resume(res)
                    }
                }
            })
        }
    }


    override suspend fun gameExists(userInfoRepository: Pair<User, LobbyInfo>): GameModel {
        val request = requestMakerGameExists((userInfoRepository.first as LoggedUser).username)

        return suspendCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    val bodyString = body?.string()
                    if (!response.isSuccessful || body == null) response.body?.let { bd ->
                        cont.resumeWithException(Exception(bodyString ?: "Unknown error"))
                    }
                    else {
                        cont.resume(gson.fromJson(bodyString, SirenMapToModel::class.java).toGame())
                    }
                }
            })
        }
    }

    private fun requestMakerCreateLobby(lobby: LobbyInfo, token: String?): Request {
        val json = gson.toJson(lobby)
        val body: RequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        val requestBuilder = Request.Builder()
            .url("https://${LOCALHOST}/games")
            .post(body)
            .addHeader("Content-Type", "application/json")

        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return requestBuilder.build()
    }

    private fun requestMakerGameExists(username: String): Request {
        val requestBuilder = Request.Builder()
            .url("https://${LOCALHOST}/games/user/$username")
            .get()
            .addHeader("Content-Type", "application/json")
        return requestBuilder.build()
    }
}

