// MainActivity.kt
package com.example.android7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.android7.data.AppDatabase
import com.example.android7.data.Player
import com.example.android7.ui.theme.Android7Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(this)

        setContent {
            Android7Theme {
                PlayerScreen(database)
            }
        }
    }
}

@Composable
fun PlayerScreen(database: AppDatabase) {
    val players = remember { mutableStateListOf<Player>() }
    var playerName by remember { mutableStateOf("") }
    var playerTeam by remember { mutableStateOf("") }
    var playerCount by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()

    // Загрузка данных из базы при запуске
    LaunchedEffect(Unit) {
        scope.launch {
            val playerList = withContext(Dispatchers.IO) {
                database.playerDao().getAllPlayers() // Чтение из БД на IO потоке
            }
            players.clear()
            players.addAll(playerList)
            playerCount = players.size
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Player List", style = MaterialTheme.typography.titleLarge)

        // Список игроков
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(players) { player ->
                Text(text = "${player.name} - ${player.team}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поля ввода
        BasicTextField(
            value = playerName,
            onValueChange = { playerName = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(8.dp)) {
                    if (playerName.isEmpty()) Text("Player Name")
                    innerTextField()
                }
            }
        )
        BasicTextField(
            value = playerTeam,
            onValueChange = { playerTeam = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(8.dp)) {
                    if (playerTeam.isEmpty()) Text("Player Team")
                    innerTextField()
                }
            }
        )

        // Кнопка добавления игрока
        Button(
            onClick = {
                if (playerName.isNotEmpty() && playerTeam.isNotEmpty()) {
                    scope.launch {
                        val newPlayer = Player(name = playerName, team = playerTeam)

                        // Сохранение в базу данных на IO потоке
                        withContext(Dispatchers.IO) {
                            database.playerDao().insertPlayer(newPlayer)
                        }

                        // Обновление UI на главном потоке
                        withContext(Dispatchers.Main) {
                            players.add(newPlayer)
                            playerCount = players.size
                            playerName = ""
                            playerTeam = ""
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Add Player")
        }
        Button(
            onClick = {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        database.playerDao().clearAllPlayers()
                    }

                    withContext(Dispatchers.Main) {
                        players.clear()
                        playerCount = 0
                    }
                }
            },
            modifier = Modifier.weight(1f).padding(8.dp)
        ) {
            Text("Clear Database")
        }



        Text("Player Count: $playerCount", modifier = Modifier.padding(top = 8.dp))
    }
}
