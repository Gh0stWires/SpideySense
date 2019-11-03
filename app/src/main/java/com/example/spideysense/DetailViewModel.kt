package com.example.spideysense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import java.io.IOException

class DetailViewModel(val repo: CharacterRepo): ViewModel() {

    lateinit var name: String
    val ts = (System.currentTimeMillis() / 1000).toString()
    val hash = Tools.md5(ts + Tools.PRIVATE_KEY + Tools.PUBLIC_KEY)

    val detail = liveData {
        try {
            val character = repo.getCharaccter(ts, hash, "spider-man")
            emit(CharacterResource.loading(character))
            emit(CharacterResource.success(character))

        } catch (ioException: IOException) {
            emit(CharacterResource.error(ioException.toString(), null))
        }
    }


}