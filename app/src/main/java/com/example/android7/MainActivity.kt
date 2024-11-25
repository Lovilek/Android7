package com.example.android7

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.example.android7.data.AppDatabase
import com.example.android7.data.Player
import com.example.android7.data.PreferencesManager
import com.example.android7.ui.theme.Android7Theme
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var preferences: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getInstance(this)
        preferences = PreferencesManager(this)

        setContent {
            Android7Theme {
                PlayerScreen(
                    database = database,
                    preferences = preferences
                )
            }
        }
    }
}

@Composable
fun PlayerScreen(database: AppDatabase, preferences: PreferencesManager) {
    val players = remember { mutableStateListOf<Player>() } // Используем mutableStateListOf
    var playerName by remember { mutableStateOf("") }
    var playerTeam by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Загрузим игроков из базы данных при запуске
        scope.launch {
            val playerList = database.playerDao().getAllPlayers()
            Log.d("PlayerData", "Players: $playerList")
            players.clear()
            players.addAll(playerList) // Обновляем список
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Player List", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(players) { player ->
                Text("${player.name} - ${player.team}") // Отображаем имя и команду
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("Player Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = playerTeam,
            onValueChange = { playerTeam = it },
            label = { Text("Player Team") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (playerName.isNotEmpty() && playerTeam.isNotEmpty()) {
                Log.d("PlayerInput", "Name: $playerName, Team: $playerTeam") // Проверка ввода

                scope.launch {
                    database.playerDao().insertPlayerDirectly() // Вставка напрямую
                    val playerList = database.playerDao().getAllPlayers() // Обновляем список
                    players.clear()
                    players.addAll(playerList) // Обновляем UI
                    preferences.savePlayerCount(players.size) // Обновляем счётчик
                }

                // Очищаем поля ввода
                playerName = ""
                playerTeam = ""
            } else {
                Log.d(
                    "PlayerInput",
                    "Empty fields: Name: $playerName, Team: $playerTeam"
                ) // Если поля пусты
            }
        }) {
            Text("Add Player")
        }
    }
}
