package com.example.gomoku.http

import com.example.gomoku.game.GameModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.example.gomoku.game.GameService
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.model.SirenMapToModel
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class GameRequest(private val client: OkHttpClient, private val gson: Gson): GameService {

    override suspend fun getGame(userInfoRepository: Pair<User, LobbyInfo>): GameModel {

        val username = (userInfoRepository.first as LoggedUser).username
        val request = Request.Builder()
            .url("https://${LOCALHOST}/games/user/$username")
            .addHeader("accept", "application/Json")
            .get().build()

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

    override suspend fun getGameById(id: Int): GameModel {
        val request = Request.Builder()
            .url("https://${LOCALHOST}/games/$id")
            .addHeader("accept", "application/Json")
            .get().build()

        return suspendCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    val bodyString = body?.string()
                    if (!response.isSuccessful || body == null) cont.resumeWithException(Exception(bodyString ?: "Unknown error"))
                    else {
                        cont.resume(gson.fromJson(bodyString, SirenMapToModel::class.java).toGame())
                    }
                }
            })
        }
    }

    override suspend fun play(
        userInfoRepository: Pair<User, LobbyInfo>,
        row: Int,
        col: Int,
        id: Int
    ): GameModel {
        val token = userInfoRepository.first.token
        val request = Request.Builder()
            .url("https://${LOCALHOST}/games/$id")
            .addHeader("accept", "application/Json")
            .addHeader("Authorization", "Bearer $token")
            .post(
                """{
                    "row": $row,
                    "col": $col
            }""".trimMargin().toRequestBody("application/json".toMediaTypeOrNull())
            ).build()

        return suspendCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    val bodyString = body?.string()
                    if (!response.isSuccessful || body == null)
                        cont.resumeWithException(Exception(bodyString ?: "Unknown error"))
                    else {
                        cont.resume(gson.fromJson(bodyString, SirenMapToModel::class.java).toGame())
                    }
                }
            })
        }
    }

    override suspend fun forfeit(userInfoRepository: Pair<User, LobbyInfo>, id: Int) {
        val token = userInfoRepository.first.token
        val request = Request.Builder()
            .url("https://${LOCALHOST}/games/forfeit/$id")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .post("".toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        return suspendCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    val bodyString = body?.string()
                    if (!response.isSuccessful || body == null) cont.resumeWithException(Exception(bodyString ?: "Unknown error"))
                    else {
                        cont.resume(Unit)
                    }
                }
            })
        }
    }

    override suspend fun getUserById(id: String) : String {
        val request = Request.Builder()
            .url("https://${LOCALHOST}/users/$id")
            .addHeader("accept", "application/Json")
            .build()

        return suspendCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    val bodyString = body?.string()
                    if (body != null) {
                        if (!response.isSuccessful) cont.resume(bodyString ?: "Unknown error")
                        else {
                            cont.resume(gson.fromJson(bodyString,SirenMapToModel::class.java).toUser())
                        }

                    }
                }
            })
        }
    }
}

