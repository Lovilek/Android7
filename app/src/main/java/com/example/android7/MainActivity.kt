package com.example.android7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.*
import com.example.android7.data.AppDatabase
import com.example.android7.ui.theme.Android7Theme
import com.example.android7.viewmodel.MatchViewModel
import com.example.android7.viewmodel.PlayerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerViewModel = PlayerViewModel(AppDatabase.getInstance(this))
        val matchViewModel = MatchViewModel()

        setContent {
            Android7Theme {
                var currentScreen by remember { mutableStateOf("PlayersScreen") }

                AnimatedVisibility(
                    visible = currentScreen == "PlayersScreen",
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PlayersScreen(
                        viewModel = playerViewModel,
                        onNavigateToApiScreen = { currentScreen = "MatchesScreen" }
                    )
                }

                AnimatedVisibility(
                    visible = currentScreen == "MatchesScreen",
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    MatchesScreen(
                        viewModel = matchViewModel,
                        onBack = { currentScreen = "PlayersScreen" }
                    )
                }
            }
        }
    }
}
