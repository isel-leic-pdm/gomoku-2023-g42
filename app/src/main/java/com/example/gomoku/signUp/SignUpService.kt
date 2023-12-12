package com.example.gomoku.signUp

import com.example.gomoku.domain.User

interface SignUpService{

    suspend fun  createUser(username:String, password: String): User

}