package com.rriviere.f2pcatalog.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rriviere.f2pcatalog.model.Game

@Dao
interface GameDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertGameList(games: List<Game>)

  @Query("SELECT * FROM Game WHERE id = :id")
  suspend fun getGame(id: Long): Game?

  @Query("SELECT * FROM Game")
  suspend fun getGameList(): List<Game>
}
