package com.example.android7

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.android7.viewmodel.PlayerViewModel

@Composable
fun PlayersScreen(viewModel: PlayerViewModel, onNavigateToApiScreen: () -> Unit) {
    val players by viewModel.players.collectAsState()
    var playerName by remember { mutableStateOf("") }
    var playerTeam by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Players Management", style = MaterialTheme.typography.titleLarge)

        // Список игроков
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(players) { player ->
                Text(text = "${player.name} - ${player.team}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поля ввода
        TextField(
            value = playerName,
            onValueChange = { playerName = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Player Name") }
        )

        TextField(
            value = playerTeam,
            onValueChange = { playerTeam = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Player Team") }
        )

        // Кнопки
        Button(
            onClick = {
                if (playerName.isNotEmpty() && playerTeam.isNotEmpty()) {
                    viewModel.addPlayer(playerName, playerTeam)
                    playerName = ""
                    playerTeam = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Add Player")
        }

        Button(
            onClick = { viewModel.clearPlayers() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Clear Players")
        }

        // Анимированная кнопка для перехода на экран матчей
        AnimatedRefreshButton(onRefresh = onNavigateToApiScreen)
    }
}

@Composable
fun AnimatedRefreshButton(onRefresh: () -> Unit) {
    var isRotated by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 360f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    IconButton(onClick = {
        isRotated = !isRotated
        onRefresh() // Переход на экран матчей
    }) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.Refresh,
            contentDescription = "Navigate to API Screen",
            modifier = Modifier
                .size(48.dp)
                .rotate(rotationAngle)
        )
    }
}
