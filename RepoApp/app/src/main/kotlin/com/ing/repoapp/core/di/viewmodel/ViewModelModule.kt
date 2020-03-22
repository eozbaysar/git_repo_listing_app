package com.ing.repoapp.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ing.repoapp.feature.presentation.modelviews.RepoDetailsViewModel
import com.ing.repoapp.feature.presentation.modelviews.ReposViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ReposViewModel::class)
    abstract fun bindsReposViewModel(reposViewModel: ReposViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailsViewModel::class)
    abstract fun bindsMovieDetailsViewModel(repoDetailsViewModel: RepoDetailsViewModel): ViewModel


}