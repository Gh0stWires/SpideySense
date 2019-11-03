package com.example.spideysense

import retrofit2.Retrofit
import retrofit2.create

class CharacterRepo(private var retrofit: Retrofit) {
    private var api = retrofit.create(CharacterService::class.java)

    suspend fun getCharaccter(ts: String, hash: String, name: String) = api.getCharacter(ts = ts, hash = hash, name = name)
}