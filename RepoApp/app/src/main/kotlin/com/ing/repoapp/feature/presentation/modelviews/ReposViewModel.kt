package com.ing.repoapp.feature.presentation.modelviews


import androidx.lifecycle.MutableLiveData
import com.ing.repoapp.core.interactor.UseCase.None
import com.ing.repoapp.core.platform.BaseViewModel
import com.ing.repoapp.feature.domain.entities.Repo
import com.ing.repoapp.feature.domain.usecases.GetRepos
import com.ing.repoapp.feature.presentation.parcelableviews.RepoView
import javax.inject.Inject

class ReposViewModel
@Inject constructor(private val getRepos: GetRepos) : BaseViewModel() {

    var repos: MutableLiveData<List<RepoView>> = MutableLiveData()

    fun loadRepos(username:String) = getRepos(None(username)) { it.fold(::handleFailure, ::handleRepoList) }

    private fun handleRepoList(repos: List<Repo>) {
        this.repos.value = repos.map { RepoView(it.id, it.name,it.owner.avatar_url,it.owner.login,it.stargazers_count,it.open_issues) }
    }

}