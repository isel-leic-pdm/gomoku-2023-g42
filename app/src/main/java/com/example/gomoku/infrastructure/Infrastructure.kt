package com.example.gomoku.infrastructure

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.NoUser
import com.example.gomoku.user.User

interface UserInfoRepository {

    suspend fun getUserInfo(): User

    suspend fun updateUserInfo(userInfo: LoggedUser)

}

private const val USER_KEY = "USERNAME"
private const val USER_TOKEN = "TOKEN"

/**
 * A user information repository implementation supported in DataStore, the
 * modern alternative to SharedPreferences.
 */


class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {

    private val usernameKey = stringPreferencesKey(USER_KEY)
    private val tokenKey = stringPreferencesKey(USER_TOKEN)

    override suspend fun getUserInfo(): User {
        val preferences = store.data.first()
        val username = preferences[usernameKey]
        val token = preferences[tokenKey]
        return if (username != null && token != null) LoggedUser(
            username = username,
            token = token
        ) else NoUser("NON-LOGGED")
    }

    override suspend fun updateUserInfo(userInfo: LoggedUser) {
        store.edit { preferences ->
            preferences[usernameKey] = userInfo.username
            preferences[tokenKey] = userInfo.token
        }
    }
}
