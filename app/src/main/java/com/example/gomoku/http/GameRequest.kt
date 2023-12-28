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
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.example.gomoku.game.Error
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.User

class GameRequest(
    private val client: OkHttpClient,
    private val gson: Gson
): LobbyService {
    override suspend fun createLobby(userInfoRepository: Pair<User, LobbyInfo>): Either<Error, GameModel?> {
        return Either.Left(Error("Not implemented"))
    }

    override suspend fun gameExists(userInfoRepository: Pair<User, LobbyInfo>): Either<Error, GameModel?> {
        val request = requestMakerGameExists((userInfoRepository.first as LoggedUser).username)

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

    private fun requestMakerGameExists(username:String): Request {
        val requestBuilder = Request.Builder()
            .url("https://${LOCALHOST}/games/user/$username")
            .get()
            .addHeader("Content-Type", "application/json")

        return requestBuilder.build()
    }

}

