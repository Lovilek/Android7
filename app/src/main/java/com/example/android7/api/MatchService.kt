package com.example.android7.api

import retrofit2.http.GET
import retrofit2.http.Path

data class Match(
    val matchID: Int,
    val matchDateTime: String,
    val team1: Team,
    val team2: Team,
    val matchResults: List<MatchResult>
)

data class Team(
    val teamId: Int,
    val teamName: String,
    val shortName: String,
    val teamIconUrl: String
)

data class MatchResult(
    val resultID: Int,
    val resultName: String,
    val pointsTeam1: Int,
    val pointsTeam2: Int
)

interface MatchService {
    @GET("getmatchdata/bl1/{season}/{matchday}")
    suspend fun getMatchData(
        @Path("season") season: Int,
        @Path("matchday") matchday: Int
    ): List<Match>

    companion object {
        const val BASE_URL = "https://api.openligadb.de/"
    }
}
