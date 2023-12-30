package com.example.gomoku.http

import com.example.gomoku.domain.PlayerRank
import com.example.gomoku.model.SirenArrayToModel
import com.example.gomoku.rankings.RankingService
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

class RankingRequest(
    private val client: OkHttpClient,
    private val gson: Gson
) : RankingService {
    private val request by lazy {
        Request.Builder()
            .url("https://${LOCALHOST}/stats")
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun fetchRankings(): List<PlayerRank> {
        return suspendCoroutine { cont->
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
                        val gson = gson.fromJson(bodyString,SirenArrayToModel::class.java)
                        cont.resume(gson.toRankList())
                    }
                }
            })
        }
    }
}