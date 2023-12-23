package com.example.gomoku.login

import com.example.gomoku.user.User

interface LoginService{

    suspend fun  postLogin(username:String, password: String): User


}