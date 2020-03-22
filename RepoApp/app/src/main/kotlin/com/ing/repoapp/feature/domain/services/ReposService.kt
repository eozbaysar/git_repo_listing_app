package com.ing.repoapp.feature.domain.services

import com.ing.repoapp.feature.domain.repositories.ReposApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReposService
@Inject constructor(retrofit: Retrofit) : ReposApi {

    private val reposApi by lazy { retrofit.create(ReposApi::class.java) }

    override fun repos(username: String) = reposApi.repos(username)

}
