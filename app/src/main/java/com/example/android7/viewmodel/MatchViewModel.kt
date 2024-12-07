package com.example.android7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android7.api.Match
import com.example.android7.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel : ViewModel() {

    private val _matches = MutableStateFlow<List<Match>>(emptyList())
    val matches: StateFlow<List<Match>> get() = _matches

    fun fetchMatches(season: Int, matchday: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val matchData = RetrofitClient.matchService.getMatchData(season, matchday)
                _matches.value = matchData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
