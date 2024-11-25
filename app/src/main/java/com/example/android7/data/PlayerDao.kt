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

    @Query("INSERT INTO players (name, team) VALUES ('Cristiano','Alnasr' )")
    suspend fun insertPlayerDirectly()

}
