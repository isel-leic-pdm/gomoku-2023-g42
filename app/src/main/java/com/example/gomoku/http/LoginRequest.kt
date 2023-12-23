package com.example.gomoku.http

import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.LoginCreds
import com.example.gomoku.user.NoUser
import com.example.gomoku.user.User
import com.example.gomoku.login.LoginService
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


class LoginRequest (
    private val client : OkHttpClient,
    private val gson: Gson
) : LoginService{




    override suspend fun postLogin(username: String, password:String): User {

        val request = requestMaker(username, password)
        return suspendCoroutine {

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException( throw e )
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if(!response.isSuccessful || body == null)
                        it.resume(NoUser(response.code.toString()))
                    else{
                        val jsonObject = JsonParser().parse(body.string()).asJsonObject
                        val property = jsonObject.get("properties").asJsonObject
                        val token = property.get("token").asString
                        it.resume(LoggedUser(username,token))

                    }
                }

            })

        }
    }

    private fun requestMaker(username: String, password: String) : Request{
        val json = gson.toJson(LoginCreds(username,password))

        val body: RequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        return Request.Builder()
            .url("https://${LOCALHOST}/users/login")
            .post(body)
            .addHeader("Content-Type","application/json")
            .build()

    }
}