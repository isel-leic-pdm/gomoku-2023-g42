package com.example.gomoku.http

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

class RankingRequest (
    private val client : OkHttpClient,
    private val gson : Gson
) : RankingService{
    private val request by lazy{
        Request.Builder()
            .url("http://localhost:8080/home/stats")
            .addHeader("accept","application/json")
            .build()
    }


    override suspend fun fetchRankings(): String {
        return suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException( throw IllegalArgumentException() )//TODO()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if(!response.isSuccessful || body == null)
                        it.resumeWithException(throw IllegalArgumentException()) //TODO()
                    else
                        it.resume(gson.fromJson(body.string(),String::class.java))
                }

            })
        }
    }
}