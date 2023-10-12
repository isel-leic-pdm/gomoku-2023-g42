package com.example.gomoku.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import com.example.demo.domain.BoardRun
import com.example.gomoku.R

@Composable
fun HomeView(){
    Column {
        Text(text = "GOMUKU" )
        Image(painter = painterResource(id = R.drawable.logoGomoku ) )

    }
}