package com.ainsigne.mobiletechchallenge.di

import com.ainsigne.mobiletechchallenge.viewmodel.MovieDetailsRepository
import com.ainsigne.mobiletechchallenge.viewmodel.MovieDetailsViewModel
import com.ainsigne.mobiletechchallenge.viewmodel.MovieListRepository
import com.ainsigne.mobiletechchallenge.viewmodel.MovieListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * View model module for containing repositories and view models
 */
val viewModelModule = module {


    single {
        MovieListRepository(get(), get(), get())
    }

    viewModel {
        MovieListViewModel(get())
    }


    single {
        MovieDetailsRepository(get(), get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }

}