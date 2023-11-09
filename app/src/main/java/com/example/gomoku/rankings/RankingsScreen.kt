package com.example.gomoku.rankings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.gson.Gson
import okhttp3.OkHttpClient

@Composable
fun rankingScreen(onInfoRequested:(OkHttpClient, Gson) -> String) {
    Text("Rankings")

}