package com.example.android7

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.android7.api.Match
import com.example.android7.viewmodel.MatchViewModel

@Composable
fun MatchesScreen(viewModel: MatchViewModel, onBack: () -> Unit) {
    val matches by viewModel.matches.collectAsState()
    var season by remember { mutableStateOf("2020") }
    var matchday by remember { mutableStateOf("1") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Matches", style = MaterialTheme.typography.titleLarge)

        // Поля для ввода сезона и игрового дня
        TextField(
            value = season,
            onValueChange = { season = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Season") }
        )

        TextField(
            value = matchday,
            onValueChange = { matchday = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Matchday") }
        )

        // Кнопка загрузки матчей
        Button(
            onClick = {
                if (season.isNotEmpty() && matchday.isNotEmpty()) {
                    viewModel.fetchMatches(season.toInt(), matchday.toInt())
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Fetch Matches")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список матчей
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(matches) { match ->
                MatchItem(match)
            }
        }

        // Кнопка возврата
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Back to Players")
        }
    }
}

@Composable
fun MatchItem(match: Match) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamView(teamName = match.team1.teamName, logoUrl = match.team1.teamIconUrl)
            Text("VS", style = MaterialTheme.typography.bodyMedium)
            TeamView(teamName = match.team2.teamName, logoUrl = match.team2.teamIconUrl)
        }

        match.matchResults.firstOrNull()?.let { result ->
            Text("Result: ${result.pointsTeam1} - ${result.pointsTeam2}")
        }
    }
}

@Composable
fun TeamView(teamName: String, logoUrl: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(model = logoUrl),
            contentDescription = "Team Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(teamName, style = MaterialTheme.typography.bodyLarge)
    }
}
