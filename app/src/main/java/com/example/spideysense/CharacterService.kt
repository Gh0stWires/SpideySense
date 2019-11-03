package com.example.spideysense

import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("v1/public/characters")
    suspend fun getCharacter(@Query("ts") ts: String, @Query("apikey") apikey: String = Tools.PUBLIC_KEY, @Query("hash") hash: String, @Query("name") name: String): Character
}