package com.example.spideysense

data class CharacterResource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun success(data: Character): CharacterResource<Character> {
            return CharacterResource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun error(msg: String, data: Character?): CharacterResource<Character> {
            return CharacterResource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun loading(data: Character): CharacterResource<Character> {
            return CharacterResource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}