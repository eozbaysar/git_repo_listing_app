package com.ing.repoapp.core.di

import com.ing.repoapp.AndroidApplication
import com.ing.repoapp.core.di.viewmodel.ViewModelModule
import com.ing.repoapp.core.navigation.RouteActivity
import com.ing.repoapp.feature.presentation.views.RepoDetailsFragment
import com.ing.repoapp.feature.presentation.views.ReposFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])

interface ApplicationComponent {

    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)
    fun inject(reposFragment: ReposFragment)
    fun inject(repoDetailsFragment: RepoDetailsFragment)

}
