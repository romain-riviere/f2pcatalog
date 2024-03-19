package com.rriviere.f2pcatalog.di

import android.app.Application
import androidx.room.Room
import com.rriviere.f2pcatalog.R
import com.rriviere.f2pcatalog.model.Game
import com.rriviere.f2pcatalog.persistence.AppDatabase
import com.rriviere.f2pcatalog.persistence.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideAppDatabase(application: Application): AppDatabase {
    return Room
      .databaseBuilder(
        application,
        AppDatabase::class.java,
        application.getString(R.string.database)
      )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  @Singleton
  fun provideGameDao(appDatabase: AppDatabase): GameDao {
    return appDatabase.gameDao()
  }
}
