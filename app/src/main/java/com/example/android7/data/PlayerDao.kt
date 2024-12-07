package com.example.android7.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<Player> // Указываем List<Player>

    @Insert
    suspend fun insertPlayer(player: Player) // Указываем Player

    @Query("INSERT INTO players (name, team) VALUES ('Messi','Inter' )")
    suspend fun insertPlayerDirectly()

    @Query("DELETE FROM players")
    suspend fun clearAllPlayers()

    @Insert
    suspend fun insertPlayers(players: List<Player>) // Этот метод добавляет список игроков



}
