package com.ing.repoapp.feature.domain.usecases

import com.ing.repoapp.core.interactor.UseCase
import com.ing.repoapp.core.interactor.UseCase.None
import com.ing.repoapp.feature.domain.entities.Repo
import com.ing.repoapp.feature.domain.repositories.ReposRepository
import javax.inject.Inject

class GetRepos
@Inject constructor(private val reposRepository: ReposRepository) : UseCase<List<Repo>, None>() {

    override suspend fun run(params: None) = reposRepository.repos(params.custom)

}
