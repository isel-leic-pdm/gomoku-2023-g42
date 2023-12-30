package com.example.gomoku.infrastructure

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.gomoku.lobby.LobbyInfo
import com.example.gomoku.user.LoggedUser
import com.example.gomoku.user.NoUser
import com.example.gomoku.user.User

interface UserInfoRepository {

    suspend fun getUserInfo(): Pair<User, LobbyInfo>

    suspend fun updateUserInfo(userInfo: LoggedUser, lobbyInfo: LobbyInfo? )
}

private const val USER_KEY = "USERNAME"
private const val TOKEN_KEY = "TOKEN"
private const val RULES_KEY = "RULES"
private const val VARIANT_KEY = "VARIANT"
private const val SIZE_KEY = "SIZE"
//private const val PLAYER_ID_KEY = "PLAYER_ID"


/**
 * A user information repository implementation supported in DataStore, the
 * modern alternative to SharedPreferences.
 */


class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {

    private val usernameKey = stringPreferencesKey(USER_KEY)
    private val tokenKey = stringPreferencesKey(TOKEN_KEY)
    //private val playerIdKey = stringPreferencesKey(PLAYER_ID_KEY)
    private val rulesKey = stringPreferencesKey(RULES_KEY)
    private val variantKey = stringPreferencesKey(VARIANT_KEY)
    private val sizeKey = stringPreferencesKey(SIZE_KEY)

    override suspend fun getUserInfo(): Pair<User, LobbyInfo> {
        val preferences = store.data.first()
        val username = preferences[usernameKey]
        val token = preferences[tokenKey]
        //val playerId = preferences[playerIdKey]
        val user = if (username != null && token != null /*&& playerId != null*/) LoggedUser(
            username = username,
            token = token,
            //playerId = playerId.toInt()
        ) else NoUser("NON-LOGGED")
        val rules = preferences[rulesKey]
        val variant = preferences[variantKey]
        val size = preferences[sizeKey]
        val lobby = if(rules !=null && variant!= null && size != null) LobbyInfo(rules,variant, size) else LobbyInfo()
        return Pair(user,lobby)
    }

    override suspend fun updateUserInfo(userInfo: LoggedUser, lobbyInfo: LobbyInfo? ) {
        val settings = lobbyInfo ?: LobbyInfo()
        store.edit { preferences ->
            preferences[usernameKey] = userInfo.username
            preferences[tokenKey] = userInfo.token
            //preferences[playerIdKey] = userInfo.playerId.toString()
            preferences[rulesKey] = settings.rules
            preferences[variantKey] = settings.variant
            preferences[sizeKey] = settings.boardSize
        }
    }
}
