//package com.example.android7
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.android7.viewmodel.PlayerViewModel
//
//@Composable
//fun ApiPlayerScreen(viewModel: PlayerViewModel, onBack: () -> Unit) {
//    val apiPlayers by viewModel.apiPlayers.collectAsState()
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("API Player List", style = MaterialTheme.typography.titleLarge)
//
//        LazyColumn(modifier = Modifier.weight(1f)) {
//            items(apiPlayers) { player ->
//                Text(text = "${player.name} - ${player.team}")
//            }
//        }
//
//        Button(
//            onClick = onBack,
//            modifier = Modifier.fillMaxWidth().padding(8.dp)
//        ) {
//            Text("Back")
//        }
//    }
//}
