package com.example.spideysense

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object AppModule {
    private val detailViewModel = module {
        viewModel { DetailViewModel(get()) }
    }

    fun all() = DataModule.all() + listOf(detailViewModel)
}