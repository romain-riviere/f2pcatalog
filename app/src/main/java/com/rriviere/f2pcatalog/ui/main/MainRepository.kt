package com.rriviere.f2pcatalog.ui.main

import androidx.annotation.WorkerThread
import com.rriviere.f2pcatalog.model.Game
import com.rriviere.f2pcatalog.network.F2PService
import com.rriviere.f2pcatalog.persistence.GameDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val f2pService: F2PService,
    private val gameDao: GameDao
) {

    init {
        Timber.d("Injection MainRepository")
    }

    @WorkerThread
    fun loadGames(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val games: List<Game> = gameDao.getGameList()
        if (games.isEmpty()) {
            // request API network call asynchronously.
            f2pService.fetchGamesList()
                // handle the case when the API request gets a success response.
                .suspendOnSuccess {
                    gameDao.insertGameList(data)
                    emit(data)
                }
                // handle the case when the API request is fails.
                // e.g. internal server error.
                .onFailure {
                  Timber.e(message())
                    onError(message())
                }
        } else {
            emit(games)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}
