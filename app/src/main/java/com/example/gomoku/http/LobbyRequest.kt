package com.example.gomoku.http

import com.example.demo.domain.BoardRun
import com.example.demo.domain.toPlayer

import com.example.gomoku.game.Either
import com.example.gomoku.game.GameModel
import com.example.gomoku.lobby.LobbyService
import com.google.gson.Gson
import com.google.gson.JsonParser
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
import com.example.gomoku.game.Error
import com.example.gomoku.lobby.LobbyInfo

class LobbyRequest(
    private val client: OkHttpClient,
    private val gson: Gson
) : LobbyService {

    override suspend fun createLobby(
        lobby: LobbyInfo, token: String?
    ): Either<Error, GameModel?> {
        val request = requestMakerCreateLobby(LobbyInfo(lobby.rules, lobby.variant, lobby.boardSize), token)

        return suspendCoroutine { cont ->

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(throw e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) response.body?.let { bd ->
                        cont.resume(Either.Left(Error(bd.toString())))
                    }
                    else {
                        val jsonObject = JsonParser().parse(body.string()).asJsonObject

                        val properties = try {
                            jsonObject.get("properties").asJsonObject
                        } catch (e: Exception) {
                            null
                        }
                        val gameModel: GameModel? = properties?.let {
                            val size = it.getAsJsonPrimitive("boardSize").asInt
                            val _rules =
                                it.getAsJsonObject("board").getAsJsonPrimitive("rules").asString
                            val _variant =
                                it.getAsJsonObject("board").getAsJsonPrimitive("variant").asString
                            val turn =
                                it.getAsJsonObject("board").getAsJsonPrimitive("turn").asString

                            val board =
                                BoardRun(emptyMap(), size, _rules, _variant, turn.toPlayer())
                            GameModel(
                                id = it.getAsJsonPrimitive("id").asInt,
                                board = board,
                                state = it.getAsJsonPrimitive("state").asString,
                                playerB = it.getAsJsonPrimitive("playerB").asInt,
                                playerW = it.getAsJsonPrimitive("playerW").asInt,
                                boardSize = it.getAsJsonPrimitive("boardSize").asInt
                            )
                        }
                        cont.resume(Either.Right(gameModel))
                    }
                }
            })
        }
    }

    override suspend fun gameExists(username: String): Either<Error, GameModel?> {
        val request = requestMakerGameExists(username)

        return suspendCoroutine { cont ->

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(throw e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) response.body?.let { bd ->
                        cont.resume(Either.Left(Error(bd.toString())))
                    }
                    else {
                        val jsonObject = JsonParser().parse(body.string()).asJsonObject

                        val properties = try {
                            jsonObject.get("properties").asJsonObject
                        } catch (e: Exception) {
                            null
                        }
                        val gameModel: GameModel? = properties?.let {
                            val size = it.getAsJsonPrimitive("boardSize").asInt
                            val _rules =
                                it.getAsJsonObject("board").getAsJsonPrimitive("rules").asString
                            val _variant =
                                it.getAsJsonObject("board").getAsJsonPrimitive("variant").asString
                            val turn =
                                it.getAsJsonObject("board").getAsJsonPrimitive("turn").asString

                            val board =
                                BoardRun(emptyMap(), size, _rules, _variant, turn.toPlayer())
                            GameModel(
                                id = it.getAsJsonPrimitive("id").asInt,
                                board = board,
                                state = it.getAsJsonPrimitive("state").asString,
                                playerB = it.getAsJsonPrimitive("playerB").asInt,
                                playerW = it.getAsJsonPrimitive("playerW").asInt,
                                boardSize = it.getAsJsonPrimitive("boardSize").asInt
                            )
                        }
                        cont.resume(Either.Right(gameModel))
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

    private fun requestMakerGameExists(username:String): Request {
        //val json = gson.toJson(username)
        //val body: RequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        val requestBuilder = Request.Builder()
            .url("https://${LOCALHOST}/games/user/$username")
            .get()
            .addHeader("Content-Type", "application/json")

        return requestBuilder.build()
    }

}

