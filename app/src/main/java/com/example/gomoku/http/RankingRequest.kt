package com.example.gomoku.http

import com.example.gomoku.domain.PlayerRank
import com.example.gomoku.rankings.RankingService
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


// localhost - 10.0.2.2
const val LOCALHOST = "https://1d88-2001-8a0-612f-f500-f8e8-7a1e-1dbc-522c.ngrok-free.app"
class RankingRequest (
    private val client : OkHttpClient,
    private val gson : Gson
) : RankingService{
    private val request by lazy{
        Request.Builder()
            .url("${LOCALHOST}/stats")
            .addHeader("accept","application/json")
            .build()
    }


    override suspend fun fetchRankings(): List<PlayerRank> {
        return suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException( throw e )//TODO()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if(!response.isSuccessful || body == null)
                        it.resumeWithException(throw IllegalArgumentException("${response.code}")) //TODO()
                    else{
                        val jsonObject = JsonParser().parse(body.string()).asJsonObject
                        val playersList = jsonObject.asJsonObject.get("properties").asJsonArray
                        val rankings = playersList.map { property ->
                            val propertyObj = property.asJsonObject
                            PlayerRank(
                                propertyObj.get("username").asString,
                                propertyObj.get("rank").asString,
                                propertyObj.get("playedGames").asString,
                                propertyObj.get("wonGames").asString,
                                propertyObj.get("lostGames").asString
                            )
                        }
                        it.resume(rankings)
                    }

                }

            })
        }
    }
}