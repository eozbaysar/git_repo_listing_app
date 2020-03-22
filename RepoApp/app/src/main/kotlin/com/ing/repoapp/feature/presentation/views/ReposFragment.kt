package com.ing.repoapp.feature.presentation.views

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandocejas.sample.core.extension.viewModel
import com.ing.repoapp.R
import com.ing.repoapp.core.exception.Failure
import com.ing.repoapp.core.exception.Failure.NetworkConnection
import com.ing.repoapp.core.exception.Failure.ServerError
import com.ing.repoapp.core.navigation.Navigator
import com.ing.repoapp.core.platform.BaseFragment
import com.ing.repoapp.feature.domain.entities.RepoFailure.ListNotAvailable
import com.ing.repoapp.feature.presentation.adapters.ReposAdapter
import com.ing.repoapp.feature.presentation.modelviews.ReposViewModel
import com.ing.repoapp.feature.presentation.parcelableviews.RepoView
import failure
import invisible
import kotlinx.android.synthetic.main.fragment_repos.*
import observe
import visible
import javax.inject.Inject


class ReposFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var reposAdapter: ReposAdapter
    private lateinit var reposViewModel: ReposViewModel

    override fun layoutId() = R.layout.fragment_repos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        loadActionBar(getString(R.string.home),false)
        reposViewModel = viewModel(viewModelFactory) {
            observe(repos, ::renderReposList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    var search = ""

    private fun initializeView() {
        reposList.layoutManager = LinearLayoutManager(context)
        reposList.adapter = reposAdapter

        btn_fetchrepos.setOnClickListener {
            hideKeyboardFrom()
            search = et_search.text.toString()
            if(search.isEmpty()) {
                notify(R.string.empty_field)
                return@setOnClickListener
            }
            loadRepoList(search)
        }
        reposAdapter.clickListener = { repo, navigationExtras ->
                    navigator.showRepoDetails(activity!!, repo, navigationExtras)
        }
    }

    private fun loadRepoList(username: String) {
        reposList.adapter = reposAdapter
        reposAdapter.collection=emptyList()
        reposAdapter.notifyDataSetChanged()
        firstTimeView.invisible()
        emptyView.invisible()
        reposList.visible()
        //showProgress()
        reposViewModel.loadRepos(username)
    }

    private fun renderReposList(repos: List<RepoView>?) {
        reposAdapter.collection = repos.orEmpty()
        //hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_repo_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        reposList.invisible()
        emptyView.visible()
        //hideProgress()
        notifyWithAction(message, R.string.action_refresh, { loadRepoList(search) })
    }

    override fun onResume() {
        super.onResume()
        reposList.adapter = reposAdapter
        reposAdapter.notifyDataSetChanged()
    }

}
