package com.example.android7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android7.api.RetrofitClient
import com.example.android7.data.AppDatabase
import com.example.android7.data.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerViewModel(private val database: AppDatabase) : ViewModel() {

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> get() = _players

    private val _playerCount = MutableStateFlow(0)
    val playerCount: StateFlow<Int> get() = _playerCount

    fun loadPlayers() {
        viewModelScope.launch {
            val playerList = withContext(Dispatchers.IO) {
                database.playerDao().getAllPlayers()
            }
            _players.value = playerList
            _playerCount.value = playerList.size
        }
    }

    fun addPlayer(name: String, team: String) {
        viewModelScope.launch {
            val newPlayer = Player(name = name, team = team)
            withContext(Dispatchers.IO) {
                database.playerDao().insertPlayer(newPlayer)
            }
            loadPlayers()
        }
    }

    fun clearPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.playerDao().clearAllPlayers()
            }
            loadPlayers()
        }
    }


}
