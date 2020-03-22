package com.ing.repoapp.feature.presentation.modelviews

import androidx.lifecycle.MutableLiveData
import com.ing.repoapp.core.platform.BaseViewModel
import com.ing.repoapp.feature.presentation.parcelableviews.RepoView
import javax.inject.Inject

class RepoDetailsViewModel
@Inject constructor() : BaseViewModel() {

    var repoDetails: MutableLiveData<RepoView> = MutableLiveData()

    fun loadRepoDetails(repoView: RepoView) = handleRepoDetails(repoView)

    private fun handleRepoDetails(repoView: RepoView) {
        this.repoDetails.value = repoView
    }

}