package com.rriviere.f2pcatalog.network

import com.rriviere.f2pcatalog.model.Game
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface F2PService {

  @GET("games")
  suspend fun fetchGamesList(): ApiResponse<List<Game>>
}
