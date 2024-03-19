package com.rriviere.f2pcatalog.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rriviere.f2pcatalog.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun gameDao(): GameDao
}
