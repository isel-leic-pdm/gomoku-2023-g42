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

        private val _userInfoFlow: MutableStateFlow<IOState<User?>> = MutableStateFlow(Idle)

        /**
         * The flow of states the view model traverses.
         */
        val userInfo: StateFlow<IOState<User?>>
            get() = _userInfoFlow.asStateFlow()

        /**
         * Fetches the user information. The states the view model traverses while fetching the
         * user information published in the [userInfo] flow. These states are:
         * - [Loading] while fetching the user information;
         * - [Loaded] with the user information if the fetch succeeds;
         * @throws IllegalStateException if the view model is not in the idle state.
         */
        fun fetchUserInfo() {
            if (_userInfoFlow.value !is Idle)
                throw IllegalStateException("The view model is not in the idle state.")

            _userInfoFlow.value = Loading
            viewModelScope.launch {
                val result = runCatching { repository.getUserInfo() }
                _userInfoFlow.value = Loaded(result)
            }
        }

        /**
         * Resets the view model to the idle state. From the idle state, the user information
         * can be fetched again.
         * @throws IllegalStateException if the view model is not in the loaded state.
         */
        fun resetToIdle() {
            if (_userInfoFlow.value !is Loaded)
                throw IllegalStateException("The view model is not in the loaded state.")
            _userInfoFlow.value = Idle
        }
    }

