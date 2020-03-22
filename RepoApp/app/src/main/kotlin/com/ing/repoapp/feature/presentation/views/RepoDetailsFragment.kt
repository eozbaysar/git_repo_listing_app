package com.ing.repoapp.feature.presentation.views

import android.os.Bundle
import android.view.View
import com.fernandocejas.sample.core.extension.viewModel
import com.ing.repoapp.R
import com.ing.repoapp.core.platform.BaseFragment
import com.ing.repoapp.feature.domain.entities.realmdata.Favourite
import com.ing.repoapp.feature.presentation.modelviews.RepoDetailsViewModel
import com.ing.repoapp.feature.presentation.others.RepoDetailsAnimator
import com.ing.repoapp.feature.presentation.parcelableviews.RepoView
import io.realm.Realm
import isVisible
import kotlinx.android.synthetic.main.fragment_repos_details.*
import loadUrlAndPostponeEnterTransition
import observe
import javax.inject.Inject


class RepoDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_REPO = "param_repo"
        fun forRepo(repo: RepoView): RepoDetailsFragment {
            val repoDetailsFragment = RepoDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_REPO, repo)
            repoDetailsFragment.arguments = arguments
            return repoDetailsFragment
        }
    }

    override fun layoutId() = R.layout.fragment_repos_details

    @Inject
    lateinit var repoDetailsAnimator: RepoDetailsAnimator

    private lateinit var repoDetailsViewModel: RepoDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        repoDetailsViewModel = viewModel(viewModelFactory) {
            observe(repoDetails, ::renderRepoDetails)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoDetailsViewModel.loadRepoDetails((arguments?.get(PARAM_REPO) as RepoView))
        favourite.setOnClickListener {
            var fav:Favourite?=searchFav(repoDetailsViewModel.repoDetails.value?.id)
            if (!Realm.getDefaultInstance()
                    .isInTransaction
            ) Realm.getDefaultInstance().beginTransaction()
            when(fav){
                null-> {
                    val favourite: Favourite = Realm.getDefaultInstance().createObject<Favourite>(Favourite::class.java, repoDetailsViewModel.repoDetails.value?.id)
                    favourite.name = repoDetailsViewModel.repoDetails.value?.name
                    favourite.username = repoDetailsViewModel.repoDetails.value?.username
                    changeIcon(favourite)
                }
                else->{
                    fav.deleteFromRealm()
                    changeIcon(null)
                }
            }
            Realm.getDefaultInstance().commitTransaction()

        }
    }

    override fun onBackPressed() {
        repoDetailsAnimator.fadeInvisible(scrollView, movieDetails)
        if (favourite.isVisible())
            repoDetailsAnimator.scaleDownView(favourite)
        else
            repoDetailsAnimator.cancelTransition(userAvatar)
    }


    private fun searchFav(id: Int?): Favourite? =
        Realm.getDefaultInstance().where<Favourite>(Favourite::class.java).equalTo("id", id)
            .findFirst()

    private fun changeIcon(fav: Favourite?) {
        when (fav) {
            null -> favourite.setImageResource(R.drawable.unselected_star)
            else -> {
                favourite.setImageResource(R.drawable.selected_star)
            }
        }
    }

    private fun renderRepoDetails(repo: RepoView?) {

        repo?.let {
            with(repo) {
                activity?.let {
                    userAvatar.loadUrlAndPostponeEnterTransition(repo.avatar_url, it)
                    changeActionbarTitle(repo.name)
                    loadActionBar(repo.name,true)
                }
                repoDetailUsername.text = repo.username
                repoDetailStars.text = repo.stargazers_count.toString()
                repoDetailIssues.text = repo.open_issues.toString()
            }
        }
        changeIcon(searchFav(repo?.id))
        repoDetailsAnimator.fadeVisible(scrollView, movieDetails)
        repoDetailsAnimator.scaleUpView(favourite)

    }

}
