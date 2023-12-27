package com.example.gomoku.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gomoku.domain.IOState
import com.example.gomoku.domain.Idle
import com.example.gomoku.domain.Loaded
import com.example.gomoku.domain.Loading
import com.example.gomoku.infrastructure.UserInfoRepository
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
        private val repository: UserInfoRepository
    ) : ViewModel() {

        companion object {
            fun factory(repository: UserInfoRepository) = viewModelFactory {
                initializer { HomeViewModel(repository) }
            }
        }

        private val _userInfoFlow: MutableStateFlow<IOState<Pair<User, LobbyInfo>>> = MutableStateFlow(Idle)


        val userInfo: StateFlow<IOState<Pair<User, LobbyInfo>>>
            get() = _userInfoFlow.asStateFlow()


        fun fetchUserInfo() {

            /*if (_userInfoFlow.value !is Idle)
                throw IllegalStateException("The view model is not in the idle state.") */

            _userInfoFlow.value = Loading
            viewModelScope.launch {
                val result = runCatching { repository.getUserInfo() }
                _userInfoFlow.value = Loaded(result)
            }
        }


        fun resetToIdle() {
            if (_userInfoFlow.value !is Loaded)
                throw IllegalStateException("The view model is not in the loaded state.")
            _userInfoFlow.value = Idle
        }
    }

