package com.rriviere.f2pcatalog.di

import com.rriviere.f2pcatalog.network.F2PService
import com.rriviere.f2pcatalog.persistence.GameDao
import com.rriviere.f2pcatalog.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMainRepository(
    f2pService: F2PService,
    gameDao: GameDao
  ): MainRepository {
    return MainRepository(f2pService, gameDao)
  }
}
