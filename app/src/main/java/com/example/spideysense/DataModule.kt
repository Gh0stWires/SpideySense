package com.example.spideysense

import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {
    private val retrofitModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    private val characterRepo = module {
        single {
            CharacterRepo(get())
        }
    }

    fun all() = listOf(retrofitModule, characterRepo)
}