package com.example.gomoku.signUp

import com.example.gomoku.user.User

interface SignUpService{
    suspend fun  createUser(username:String, password: String): User
}